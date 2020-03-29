package com.gsralex.gflow.scheduler.controller;

import com.gsralex.gflow.common.entity.api.FlowResponse;
import com.gsralex.gflow.scheduler.controller.req.ExecuteFlowReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
    private static final String API_PAUSE_FLOW = "/api/flow/%d/pause";
    private static final String API_STOP_FLOW = "/api/flow/%d/stop";
    private static final String API_RETRY_FAILED_FLOW = "/api/flow/%d/retry-failed";

    @Autowired
    private RestTemplate restTemplate;

    public FlowResponse executeFlow(Long execId, Map<String, Object> params) {
        ExecuteFlowReq req = new ExecuteFlowReq().setParams(params);
        return restTemplate.postForObject(String.format(API_EXECUTE_FLOW, execId), req, FlowResponse.class);
    }

    public FlowResponse pauseFlow(Long execId) {
        return restTemplate.exchange(
                String.format(API_PAUSE_FLOW, execId), HttpMethod.PUT, null, FlowResponse.class).getBody();
    }

    public FlowResponse stopFlow(Long execId) {
        return restTemplate.exchange(
                String.format(API_STOP_FLOW, execId), HttpMethod.PUT, null, FlowResponse.class).getBody();
    }

    public FlowResponse retryFailed(Long execId) {
        return restTemplate.exchange(
                String.format(API_RETRY_FAILED_FLOW,execId),HttpMethod.PUT,null,FlowResponse.class).getBody();
    }
}
