package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.enums.JobStatus;
import com.gsralex.gflow.common.enums.JobType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class ExecuteNode {

    private Long flowJobExecId;
    private Long jobId;
    private JobType jobType;

    private JobStatus jobStatus;

    private String jobDesc;
    /**
     * 依赖
     */
    private List<ExecuteNode> preJobs = new ArrayList<>();
    /**
     * 被依赖
     */
    private List<ExecuteNode> nextJobs = new ArrayList<>();
    /**
     * 重试次数
     */
    private int retries;


    public Long getFlowJobExecId() {
        return flowJobExecId;
    }

    public ExecuteNode setFlowJobExecId(Long flowJobExecId) {
        this.flowJobExecId = flowJobExecId;
        return this;
    }

    public Long getJobId() {
        return jobId;
    }

    public ExecuteNode setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public JobType getJobType() {
        return jobType;
    }

    public ExecuteNode setJobType(JobType jobType) {
        this.jobType = jobType;
        return this;
    }

    public JobStatus getJobStatus() {
        if (Objects.isNull(jobStatus)) {
            return JobStatus.PENDING;
        }
        return jobStatus;
    }

    public ExecuteNode setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public ExecuteNode setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
        return this;
    }

    public List<ExecuteNode> getPreJobs() {
        return preJobs;
    }

    public ExecuteNode setPreJobs(List<ExecuteNode> preJobs) {
        this.preJobs = preJobs;
        return this;
    }

    public List<ExecuteNode> getNextJobs() {
        return nextJobs;
    }

    public ExecuteNode setNextJobs(List<ExecuteNode> nextJobs) {
        this.nextJobs = nextJobs;
        return this;
    }

    public int getRetries() {
        return retries;
    }

    public ExecuteNode setRetries(int retries) {
        this.retries = retries;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExecuteNode that = (ExecuteNode) o;

        return new EqualsBuilder()
                .append(flowJobExecId, that.flowJobExecId)
                .append(jobId, that.jobId)
                .append(jobType, that.jobType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(flowJobExecId)
                .append(jobId)
                .append(jobType)
                .toHashCode();
    }
}
