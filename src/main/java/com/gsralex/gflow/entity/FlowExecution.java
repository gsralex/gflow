package com.gsralex.gflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gsralex.gflow.common.enums.JobStatus;

import java.util.Date;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FlowExecution {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long flowId;
    private Long versionId;
    private Date startTime;
    private Date endTime;
    private Integer retries;
    private JobStatus status;

    public Long getId() {
        return id;
    }

    public FlowExecution setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public FlowExecution setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public Long getVersionId() {
        return versionId;
    }

    public FlowExecution setVersionId(Long versionId) {
        this.versionId = versionId;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public FlowExecution setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public FlowExecution setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getRetries() {
        return retries;
    }

    public FlowExecution setRetries(Integer retries) {
        this.retries = retries;
        return this;
    }

    public JobStatus getStatus() {
        return status;
    }

    public FlowExecution setStatus(JobStatus status) {
        this.status = status;
        return this;
    }
}
