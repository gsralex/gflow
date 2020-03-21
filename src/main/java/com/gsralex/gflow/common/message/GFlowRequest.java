package com.gsralex.gflow.common.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @date 2020/2/2
 */
public class GFlowRequest {

    private String actionName;
    private Map<String, Object> params = new HashMap<>();

    public String getActionName() {
        return actionName;
    }

    public GFlowRequest setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public GFlowRequest setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }
}
