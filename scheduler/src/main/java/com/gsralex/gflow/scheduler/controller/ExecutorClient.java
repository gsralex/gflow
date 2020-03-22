package com.gsralex.gflow.scheduler.controller;

import com.gsralex.gflow.scheduler.controller.req.ExecuteFlowReq;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/3/22
 */
public class ExecutorClient {
    private static final String API_EXECUTE_FLOW = "/api/flow/execute/%d";

    private RestTemplate restTemplate;
    private String url;

    public ExecutorClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Object executeFlow(Long execId, Map<String, Object> params) {
        ExecuteFlowReq req = new ExecuteFlowReq().setParams(params);
        return restTemplate.postForObject(url + String.format(API_EXECUTE_FLOW, execId), req, String.class);
    }


}
