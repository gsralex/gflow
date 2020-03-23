package com.gsralex.gflow.scheduler.controller;

import com.gsralex.gflow.scheduler.service.FlowExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@RestController
@RequestMapping("/api")
public class SchedulerController {


    @Autowired
    private ExecutorClient executorClient;

    @Autowired
    private FlowExecutionService flowExecutionService;


    @GetMapping("/flow/execute/{flowName}")
    public Object executeFlow(@PathVariable(value = "flowName") String flowName) {
        Long execId = flowExecutionService.createFlow(flowName, null);
        return executorClient.executeFlow(execId, null);
    }
}
