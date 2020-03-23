package com.gsralex.gflow.executor.controller;

import com.gsralex.gflow.common.constant.ResponseCode;
import com.gsralex.gflow.common.entity.api.FlowResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author gsralex
 * @date 2020/3/23
 */
@RestControllerAdvice
public class ExecutorExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object catchException(Exception e) {
        FlowResponse flowResponse = new FlowResponse();
        flowResponse.setCode(ResponseCode.ERROR_COMMON);
        flowResponse.setMsg(e.getMessage());
        return flowResponse;
    }
}
