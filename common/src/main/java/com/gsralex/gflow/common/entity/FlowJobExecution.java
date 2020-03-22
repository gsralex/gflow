package com.gsralex.gflow.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gsralex.gflow.common.enums.JobStatus;

import java.util.Date;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FlowJobExecution {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Date startTime;
    private Date endTime;
    private Integer retries;
    private Long executionId;
    private Long jobId;
    private JobStatus status;

    public Long getId() {
        return id;
    }

    public FlowJobExecution setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public FlowJobExecution setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public FlowJobExecution setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getRetries() {
        return retries;
    }

    public FlowJobExecution setRetries(Integer retries) {
        this.retries = retries;
        return this;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public FlowJobExecution setExecutionId(Long executionId) {
        this.executionId = executionId;
        return this;
    }

    public Long getJobId() {
        return jobId;
    }

    public FlowJobExecution setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public JobStatus getStatus() {
        return status;
    }

    public FlowJobExecution setStatus(JobStatus status) {
        this.status = status;
        return this;
    }
}
