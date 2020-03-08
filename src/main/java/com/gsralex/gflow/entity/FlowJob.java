package com.gsralex.gflow.entity;

import com.gsralex.gflow.common.enums.JobType;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FlowJob {

    private Long id;
    private Long flowId;
    private Long versionId;
    private String name;
    private String preJobs;
    private String nextJobs;
    private JobType jobType;
    private String jobDesc;

    public Long getId() {
        return id;
    }

    public FlowJob setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public FlowJob setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public Long getVersionId() {
        return versionId;
    }

    public FlowJob setVersionId(Long versionId) {
        this.versionId = versionId;
        return this;
    }

    public String getName() {
        return name;
    }

    public FlowJob setName(String name) {
        this.name = name;
        return this;
    }

    public String getPreJobs() {
        return preJobs;
    }

    public FlowJob setPreJobs(String preJobs) {
        this.preJobs = preJobs;
        return this;
    }

    public String getNextJobs() {
        return nextJobs;
    }

    public FlowJob setNextJobs(String nextJobs) {
        this.nextJobs = nextJobs;
        return this;
    }

    public JobType getJobType() {
        return jobType;
    }

    public FlowJob setJobType(JobType jobType) {
        this.jobType = jobType;
        return this;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public FlowJob setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
        return this;
    }
}
