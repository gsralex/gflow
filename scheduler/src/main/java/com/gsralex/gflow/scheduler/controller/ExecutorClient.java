package com.gsralex.gflow.scheduler.controller;

import com.gsralex.gflow.common.entity.api.FlowResponse;
import com.gsralex.gflow.scheduler.controller.req.ExecuteFlowReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@Service
public class ExecutorClient {
    private static final String API_EXECUTE_FLOW = "http://executor/api/flow/%d/execute";
    private static final String API_PAUSE_FLOW = "/api/flow/pause/%d";
    private static final String API_STOP_FLOW="/api/flow/stop/%d";

    @Autowired
    private RestTemplate restTemplate;

    public FlowResponse executeFlow(Long execId, Map<String, Object> params) {
        ExecuteFlowReq req = new ExecuteFlowReq().setParams(params);
        return restTemplate.postForObject(String.format(API_EXECUTE_FLOW, execId), req, FlowResponse.class);
    }

//    public FlowResponse pauseFlow(Long execId){
//        return restTemplate.patchForObject()
//    }
//
//    public FlowResponse stopFlow(Long execId){
//
//    }




}
