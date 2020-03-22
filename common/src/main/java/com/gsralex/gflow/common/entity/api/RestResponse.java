package com.gsralex.gflow.common.entity.api;

/**
 * @author gsralex
 * @date 2020/3/22
 */
public class RestResponse {
    public static final RestResponse OK = new RestResponse();

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public RestResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RestResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
