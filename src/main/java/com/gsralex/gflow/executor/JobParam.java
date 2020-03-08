package com.gsralex.gflow.executor;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class JobParam {

    private String jobDesc;
    private Integer timeout;

    public String getJobDesc() {
        return jobDesc;
    }

    public JobParam setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public JobParam setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }
}
