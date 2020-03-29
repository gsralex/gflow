package com.gsralex.gflow.scheduler.controller;

import com.gsralex.gflow.scheduler.service.FlowExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/flow/execute/{flowName}")
    public Object executeFlow(@PathVariable(value = "flowName") String flowName) {
        Long execId = flowExecutionService.createFlow(flowName, null);
        return executorClient.executeFlow(execId, null);
    }

    @PutMapping("/flow/{execId}/pause")
    public Object pauseFlow(@PathVariable(value = "execId") Long execId) {
        return executorClient.pauseFlow(execId);
    }

    @PutMapping("/flow/{execId}/stop")
    public Object stopFlow(@PathVariable(value = "execId") Long execId) {
        return executorClient.stopFlow(execId);
    }

    @PutMapping("/flow/{execId}/retry-failed")
    public Object retryFailed(@PathVariable(value = "execId") Long execId) {
        return executorClient.retryFailed(execId);
    }
}
