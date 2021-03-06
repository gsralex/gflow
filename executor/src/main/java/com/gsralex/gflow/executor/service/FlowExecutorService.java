package com.gsralex.gflow.executor.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gsralex.gflow.common.entity.FlowExecution;
import com.gsralex.gflow.common.entity.FlowJob;
import com.gsralex.gflow.common.enums.JobStatus;
import com.gsralex.gflow.common.mapper.FlowExecutionMapper;
import com.gsralex.gflow.common.mapper.FlowJobExecutionMapper;
import com.gsralex.gflow.common.mapper.FlowJobMapper;
import com.gsralex.gflow.executor.FlowExecutor;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author gsralex
 * @date 2020/2/2
 */
@Service
public class FlowExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowExecutorService.class);

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAXIMUM_POOL_SIZE = 200;
    private static final int CAPACITY_POOL_QUEUE = 1000;

    @Autowired
    private FlowJobMapper flowJobMapper;
    @Autowired
    private FlowExecutionMapper flowExecutionMapper;
    @Autowired
    private FlowJobExecutionMapper flowJobExecutionMapper;

    private static ConcurrentHashMap<Long, FlowExecutor> runningFlows = new ConcurrentHashMap<>();

    private static ExecutorService flowExecutors = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(CAPACITY_POOL_QUEUE), new ThreadFactoryBuilder()
            .setNameFormat("gFlow-flow-executor-thread-%d").build(), new ThreadPoolExecutor.AbortPolicy());


    public synchronized void executeFlow(Long execId, Map<String, Object> params) {
        Validate.notNull(execId, "请传递execId");
        FlowExecution flowExecution = getFlowExecution(execId);
        Validate.notNull(flowExecution, "找不到执行任务 execId: {}", execId);
        Validate.isTrue(flowExecution.getStatus() == JobStatus.PENDING, "任务已经执行了");
        Validate.isTrue(!runningFlows.contains(execId), "任务已经执行了");
        List<FlowJob> flowJobs = listFlowJobs(flowExecution.getFlowId(), flowExecution.getVersionId());
        Validate.noNullElements(flowJobs, "当前flow下没有包含jobs");
        FlowExecutor flowExecutor = new FlowExecutor(execId, flowJobs, null, params);
        flowExecutor.setMapper(flowJobExecutionMapper, flowExecutionMapper);
        flowExecutors.submit(flowExecutor::runFlow);
        flowExecutor.addFlowFinishedListener(this::onFlowFinished);
        runningFlows.put(execId, flowExecutor);
    }

    private void onFlowFinished(Long execId) {
        runningFlows.remove(execId);
    }

    public void pauseFlow(Long execId) {
        Validate.notNull(execId, "请传递execId");
        FlowExecutor flowExecutor = runningFlows.get(execId);
        Validate.notNull(flowExecutor, "找不到执行任务 execId: %d", execId);
        flowExecutor.pauseFlow();
        LOGGER.info("暂停flow execId: {}", execId);
    }

    public void stopFlow(Long execId) {
        Validate.notNull(execId, "请传递execId");
        FlowExecutor flowExecutor = runningFlows.get(execId);
        Validate.notNull(flowExecutor, "找不到执行任务 execId: %d", execId);
        flowExecutor.stopFlow();
        LOGGER.info("停止flow execId: {}", execId);
    }

    public void retryFailedJobs(Long execId) {
        Validate.notNull(execId, "请传递execId");
        FlowExecutor flowExecutor = runningFlows.get(execId);
        Validate.notNull(flowExecutor, "找不到执行任务 execId: %d", execId);
        flowExecutor.retryFailedJobs();
        LOGGER.info("重试失败任务 execId: {}", execId);
    }

    private FlowExecution getFlowExecution(Long executionId) {
        return flowExecutionMapper.selectById(executionId);
    }


    private List<FlowJob> listFlowJobs(Long flowId, Long versionId) {
        QueryWrapper<FlowJob> query = new QueryWrapper<>((new FlowJob()
                .setFlowId(flowId).setVersionId(versionId)));
        return flowJobMapper.selectList(query);
    }
}
