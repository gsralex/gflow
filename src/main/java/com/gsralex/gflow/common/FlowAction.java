package com.gsralex.gflow.common;

/**
 * @author gsralex
 * @date 2020/1/29
 */
public class FlowAction {

    private String action;
    private Object param;

    public String getAction() {
        return action;
    }

    public FlowAction setAction(String action) {
        this.action = action;
        return this;
    }

    public Object getParam() {
        return param;
    }

    public FlowAction setParam(Object param) {
        this.param = param;
        return this;
    }
}
