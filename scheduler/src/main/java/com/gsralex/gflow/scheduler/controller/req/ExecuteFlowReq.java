package com.gsralex.gflow.scheduler.controller.req;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/3/22
 */
public class ExecuteFlowReq {

    private Long execId;
    private Map<String,Object> params;

    public Long getExecId() {
        return execId;
    }

    public ExecuteFlowReq setExecId(Long execId) {
        this.execId = execId;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public ExecuteFlowReq setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }
}
