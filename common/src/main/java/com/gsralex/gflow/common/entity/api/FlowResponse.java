package com.gsralex.gflow.common.entity.api;

import com.gsralex.gflow.common.constant.ResponseCode;

/**
 * @author gsralex
 * @date 2020/3/22
 */
public class FlowResponse {
    public static final FlowResponse OK = new FlowResponse(ResponseCode.OK);

    private int code;
    private String msg;

    public FlowResponse() {
    }

    public FlowResponse(int code) {
        this.code = code;
    }

    public FlowResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public FlowResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public FlowResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
