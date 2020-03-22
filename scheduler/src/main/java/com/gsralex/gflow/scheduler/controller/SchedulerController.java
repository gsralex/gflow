package com.gsralex.gflow.scheduler.controller;

import com.gsralex.gflow.scheduler.controller.req.ExecuteFlowReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@RestController
@RequestMapping("/api")
public class SchedulerController {

    private static final String EXECUTE_INSTANCE = "GFLOW_EXECUTOR";


    @Autowired
    private DiscoveryClient client;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/flow/execute/{flowName}")
    public Object executeFlow(@PathVariable(value = "flowName") String flowName) {
        List<ServiceInstance> list = client.getInstances(EXECUTE_INSTANCE);
        List<String> services= client.getServices();
        ExecuteFlowReq req = new ExecuteFlowReq().setExecId(1L).setParams(new HashMap<>());
        return restTemplate.postForObject(list.get(0).getUri()+"/api/flow/execute/1", req, String.class);
    }
}
