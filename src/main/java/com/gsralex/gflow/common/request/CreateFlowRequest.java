package com.gsralex.gflow.common.request;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class CreateFlowRequest {

    private Long executionId;
    private Map<String,String> params;

    public Long getExecutionId() {
        return executionId;
    }

    public CreateFlowRequest setExecutionId(Long executionId) {
        this.executionId = executionId;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public CreateFlowRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }
}
