package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.enums.JobStatus;
import com.gsralex.gflow.entity.FlowJob;
import com.gsralex.gflow.entity.FlowJobExecution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gsralex
 * @date 2020/3/1
 */
class FlowExecutorState {

    private Long execId;
    private Map<Long, ExecuteNode> nodeMap = new HashMap<>();
    private Set<ExecuteNode> pendingNodes = new LinkedHashSet<>();
    private Set<ExecuteNode> failedNodes = new LinkedHashSet<>();

    private static final int MAX_RETRY_CNT = 3;

    private boolean retried = false;

    private List<FlowJobExecution> flowJobExecutions;

    FlowExecutorState(Long execId, List<FlowJob> flowJobs, List<FlowJobExecution> flowExecutions) {
        this.execId = execId;
        if (Objects.nonNull(flowExecutions)) {
            this.flowJobExecutions = flowExecutions;
        } else {
            this.flowJobExecutions = new ArrayList<>();
        }
        parseDagNode(flowJobs);
        initJobsStatus();
        initPendingJobs();
    }

    synchronized Set<ExecuteNode> listPendingJobs() {
        Set<ExecuteNode> pendingNodesClone = new LinkedHashSet<>(pendingNodes);
        pendingNodes.clear();
        return pendingNodesClone;
    }

    synchronized Set<ExecuteNode> listRetryFailedJobs() {
        Set<ExecuteNode> retryFailedJobs = new LinkedHashSet<>();
        if (retried) {
            for (ExecuteNode node : failedNodes) {
                if (node.getJobStatus() == JobStatus.FAILED) {
                    retryFailedJobs.add(node);
                }
            }
            //初始化重试
            retried = false;
        } else {
            for (ExecuteNode node : failedNodes) {
                if (node.getJobStatus() == JobStatus.FAILED) {
                    if (node.getRetries() < MAX_RETRY_CNT) {
                        retryFailedJobs.add(node);
                    }
                }
            }
        }
        return retryFailedJobs;
    }

    boolean isFinished() {
        for (ExecuteNode node : nodeMap.values()) {
            boolean nodeFinished = false;
            if (node.getJobStatus() == JobStatus.SUCCESS || node.getJobStatus() == JobStatus.STOPPED) {
                nodeFinished = true;
            } else if (node.getJobStatus() == JobStatus.FAILED) {
                if (node.getRetries() >= MAX_RETRY_CNT) {
                    nodeFinished = true;
                }
            }
            if (!nodeFinished) {
                return false;
            }
        }
        return true;
    }

    synchronized void updateNodeStatus(ExecuteNode node, JobStatus jobStatus) {
        node.setJobStatus(jobStatus);
        if (jobStatus == JobStatus.SUCCESS) {
            failedNodes.remove(node);
            for (ExecuteNode next : node.getNextJobs()) {
                long successCnt = next.getPreJobs().stream().filter(j -> j.getJobStatus() == JobStatus.SUCCESS).count();
                if (next.getPreJobs().size() == successCnt) {
                    pendingNodes.add(next);
                }
            }
        } else if (jobStatus == JobStatus.FAILED) {
            failedNodes.add(node);
        }
    }

    private void parseDagNode(List<FlowJob> flowJobs) {
        for (FlowJob flowJob : flowJobs) {
            nodeMap.put(flowJob.getId(),
                    new ExecuteNode().setJobId(flowJob.getId()).setJobType(flowJob.getJobType())
                            .setJobStatus(JobStatus.PENDING).setJobDesc(flowJob.getJobDesc()));
        }
        for (FlowJob flowJob : flowJobs) {
            ExecuteNode executeNode = nodeMap.get(flowJob.getId());
            if (StringUtils.isNotBlank(flowJob.getNextJobs())) {
                List<Long> nextJobIds = Arrays.stream(StringUtils.split(flowJob.getNextJobs(), ","))
                        .map(Long::parseLong).collect(Collectors.toList());
                for (Long nextJobId : nextJobIds) {
                    executeNode.getNextJobs().add(nodeMap.get(nextJobId));
                }
            }
            if (StringUtils.isNotBlank(flowJob.getPreJobs())) {
                List<Long> preJobIds = Arrays.stream(StringUtils.split(flowJob.getPreJobs(), ","))
                        .map(Long::parseLong).collect(Collectors.toList());
                for (Long preJobId : preJobIds) {
                    executeNode.getPreJobs().add(nodeMap.get(preJobId));
                }
            }
        }
    }

    private void initJobsStatus() {
        if (CollectionUtils.isEmpty(flowJobExecutions)) {
            return;
        }
        for (FlowJobExecution execution : flowJobExecutions) {
            nodeMap.get(execution.getJobId()).setJobStatus(execution.getStatus());
        }
    }

    private void initPendingJobs() {
        for (ExecuteNode node : nodeMap.values()) {
            if (node.getJobStatus() == JobStatus.PENDING) {
                //没有不成功的
                if (!node.getPreJobs().stream().anyMatch(j -> j.getJobStatus() != JobStatus.SUCCESS)) {
                    pendingNodes.add(node);
                }
            }
        }
    }

    Long getExecId() {
        return execId;
    }

    void setRetried(boolean retried) {
        this.retried = retried;
    }
}
