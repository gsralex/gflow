package com.gsralex.gflow.common.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @date 2020/2/2
 */
public class GFlowMessage {

    private String actionName;
    private Map<String, Object> params = new HashMap<>();

    public String getActionName() {
        return actionName;
    }

    public GFlowMessage setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public GFlowMessage setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }
}
