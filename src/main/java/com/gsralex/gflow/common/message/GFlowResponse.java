package com.gsralex.gflow.common.message;

/**
 * @author gsralex
 * @date 2020/3/21
 */
public class GFlowResponse {

    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public GFlowResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public GFlowResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public GFlowResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
