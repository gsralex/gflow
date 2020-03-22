package com.gsralex.gflow.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gsralex.gflow.common.entity.FlowExecution;
import com.gsralex.gflow.common.entity.FlowJob;
import com.gsralex.gflow.common.entity.FlowJobExecution;
import com.gsralex.gflow.common.enums.JobStatus;
import com.gsralex.gflow.common.enums.JobType;
import com.gsralex.gflow.common.mapper.FlowExecutionMapper;
import com.gsralex.gflow.common.mapper.FlowJobExecutionMapper;
import com.gsralex.gflow.executor.executors.ShellJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FlowExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlowExecutor.class);

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAXIMUM_POOL_SIZE = 200;
    private static final int CAPACITY_POOL_QUEUE = 1000;

    private static final int PAUSE_WAIT_MS = 10000;
    private static final int RUNNING_WAIT_MS = 10000;

    private static ExecutorService jobExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(CAPACITY_POOL_QUEUE), new ThreadFactoryBuilder()
            .setNameFormat("gFlow-job-executor-thread-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    private FlowExecutorState flowExecutorState;
    private boolean paused = false;
    private final Object pauseObject = new Object();
    private boolean stopped = false;
    private final Object runningObject = new Object();

    private List<Consumer<Long>> flowFinishedListeners = new ArrayList<>();


    private FlowJobExecutionMapper flowJobExecutionMapper;
    private FlowExecutionMapper flowExecutionMapper;

    private Long execId;

    public FlowExecutor(Long execId,
                        List<FlowJob> flowJobs,
                        List<FlowJobExecution> flowExecutions,
                        Map<String, Object> params) {
        this.execId = execId;
        this.flowExecutorState = new FlowExecutorState(execId, flowJobs, flowExecutions);
    }

    public void setMapper(FlowJobExecutionMapper flowJobExecutionMapper,
                          FlowExecutionMapper flowExecutionMapper) {
        this.flowJobExecutionMapper = flowJobExecutionMapper;
        this.flowExecutionMapper = flowExecutionMapper;
    }

    public void runFlow() {
        try {
            updateFlow(JobStatus.RUNNING);
            while (!flowExecutorState.isFinished()) {
                while (paused) {
                    synchronized (pauseObject) {
                        pauseObject.wait(PAUSE_WAIT_MS);
                    }
                }
                if (stopped) {
                    //kill running jobs
                    break;
                } else {
                    Set<ExecuteNode> pendingJobs = flowExecutorState.listPendingJobs();
                    for (ExecuteNode job : pendingJobs) {
                        executeJob(job);
                    }
                    for (ExecuteNode retryFailedJob : flowExecutorState.listRetryFailedJobs()) {
                        executeJob(retryFailedJob);
                    }
                    synchronized (runningObject) {
                        runningObject.wait(RUNNING_WAIT_MS);
                    }
                }
            }
        } catch (Exception e) {
            updateFlowFinished(JobStatus.FAILED);
        }
        if (stopped) {
            updateFlowFinished(JobStatus.STOPPED);
        } else {
            updateFlowFinished(JobStatus.SUCCESS);
        }
        notifyFlowFinishedListener();
        LOGGER.info("flow finished execId: {}", execId);
    }

    private void executeJob(ExecuteNode node) {
        jobExecutor.submit(() -> {
            boolean success;
            try {
                if (Objects.isNull(node.getFlowJobExecId())) {
                    FlowJobExecution jobExecution = new FlowJobExecution()
                            .setStatus(JobStatus.RUNNING).setStartTime(new Date());
                    flowJobExecutionMapper.insert(jobExecution);
                    node.setFlowJobExecId(jobExecution.getId());
                } else {
                    flowJobExecutionMapper.updateById(new FlowJobExecution()
                            .setId(node.getFlowJobExecId()).setStatus(JobStatus.RUNNING));
                }
                flowExecutorState.updateNodeStatus(node, JobStatus.RUNNING);
                if (node.getJobType() == JobType.SHELL) {
                    ShellJobExecutor shellJobExecutor = new ShellJobExecutor(node);
                    shellJobExecutor.execute();
                }
                success = true;
            } catch (Exception e) {
                LOGGER.error("execute flow job", e);
                success = false;
            }
            if (success) {
                updateFlowJobFinished(JobStatus.SUCCESS);
                flowExecutorState.updateNodeStatus(node, JobStatus.SUCCESS);
            } else {
                updateFlowJobFinished(JobStatus.FAILED);
                flowExecutorState.updateNodeStatus(node, JobStatus.FAILED);
            }
            synchronized (runningObject) {
                runningObject.notifyAll();
            }
        });
    }


    /**
     * 暂停flow
     */
    public void pauseFlow() {
        paused = true;
        updateFlow(JobStatus.PAUSED);
    }

    /**
     * 恢复flow
     */
    public void resumeFlow() {
        paused = false;
        synchronized (runningObject) {
            runningObject.notifyAll();
        }
        updateFlow(JobStatus.RUNNING);
    }

    /**
     * 停止flow
     */
    public void stopFlow() {
        if (paused) {
            resumeFlow();
        }
        synchronized (runningObject) {
            runningObject.notifyAll();
        }
        stopped = true;
    }

    /**
     * 重试失败任务
     */
    public synchronized void retryFailedJobs() {
        this.flowExecutorState.setRetried(true);
        synchronized (runningObject) {
            runningObject.notifyAll();
        }
    }

    private void updateFlow(JobStatus jobStatus) {
        flowExecutionMapper.updateById(new FlowExecution().setId(flowExecutorState.getExecId())
                .setStatus(jobStatus));
    }

    private void updateFlowFinished(JobStatus jobStatus) {
        flowExecutionMapper.updateById(new FlowExecution().setId(flowExecutorState.getExecId())
                .setEndTime(new Date())
                .setStatus(jobStatus));
    }

    private void updateFlowJobFinished(JobStatus jobStatus) {
        flowJobExecutionMapper.updateById(new FlowJobExecution().setId(this.flowExecutorState.getExecId())
                .setEndTime(new Date()).setStatus(jobStatus));
    }

    public void addFlowFinishedListener(Consumer<Long> consumer) {
        flowFinishedListeners.add(consumer);
    }

    private void notifyFlowFinishedListener() {
        for (Consumer<Long> listener : this.flowFinishedListeners) {
            listener.accept(this.execId);
        }
    }
}


