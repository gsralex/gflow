package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.enums.JobStatus;
import com.gsralex.gflow.entity.FlowJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FlowExecutorState {

    private Long execId;
    private Map<Long, ExecuteNode> nodeMap = new HashMap<>();
    private volatile Set<ExecuteNode> pendingNodes = new LinkedHashSet<>(100);
    private volatile Set<ExecuteNode> failedNodes = new LinkedHashSet<>(100);

    private static final int MAX_RETRY_CNT = 3;

    private boolean retried = false;

    public FlowExecutorState(Long execId, List<FlowJob> flowJobs) {
        this.execId = execId;
        parseDagNode(flowJobs);
        initPendingJobs();
    }

    public synchronized Set<ExecuteNode> listPendingJobs() {
        Set<ExecuteNode> pendingNodesClone= new LinkedHashSet<>(pendingNodes);
        pendingNodes.clear();
        return pendingNodesClone;
    }

    public synchronized Set<ExecuteNode> listRetryFailedJobs() {
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

    public boolean isFinished() {
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

    public synchronized void updateNodeStatus(ExecuteNode node, JobStatus jobStatus) {
        node.setJobStatus(jobStatus);
        if (jobStatus == JobStatus.SUCCESS) {
            if (failedNodes.contains(node)) {
                failedNodes.remove(node);
            }
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
                List<Long> nextJobIds = Arrays.asList(StringUtils.split(flowJob.getNextJobs(), ","))
                        .stream().map(n -> Long.parseLong(n)).collect(Collectors.toList());
                for (Long nextJobId : nextJobIds) {
                    executeNode.getNextJobs().add(nodeMap.get(nextJobId));
                }
            }
            if (StringUtils.isNotBlank(flowJob.getPreJobs())) {
                List<Long> preJobIds = Arrays.asList(StringUtils.split(flowJob.getPreJobs(), ","))
                        .stream().map(n -> Long.parseLong(n)).collect(Collectors.toList());
                for (Long preJobId : preJobIds) {
                    executeNode.getPreJobs().add(nodeMap.get(preJobId));
                }
            }
        }
    }

    private void initPendingJobs() {
        for (ExecuteNode node : nodeMap.values()) {
            if (CollectionUtils.isEmpty(node.getPreJobs())) {
                pendingNodes.add(node);
            }
        }
    }

    public Long getExecId() {
        return execId;
    }

    public void setRetried(boolean retried) {
        this.retried = retried;
    }
}
