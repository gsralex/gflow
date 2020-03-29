package com.gsralex.gflow.executor.controller;

import com.gsralex.gflow.common.entity.api.FlowResponse;
import com.gsralex.gflow.executor.service.FlowExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@RestController
@RequestMapping("/api")
public class ExecutorController {

    @Autowired
    private FlowExecutorService flowExecutorService;

    @PostMapping("/flow/{execId}/execute")
    public Object executeFlow(@PathVariable(value = "execId") Long execId,
                              Map<String, Object> params) {
        flowExecutorService.executeFlow(execId, params);
        return FlowResponse.OK;
    }

    @PutMapping("/flow/{execId}/pause")
    public Object pauseFlow(@PathVariable(value = "execId") Long execId) {
        flowExecutorService.pauseFlow(execId);
        return FlowResponse.OK;
    }

    @PutMapping("/flow/{execId}/stop")
    public Object stopFlow(@PathVariable(value = "execId") Long execId) {
        flowExecutorService.stopFlow(execId);
        return FlowResponse.OK;
    }

    @PutMapping("/flow/{execId}/retry-failed")
    public Object retryFailed(@PathVariable(value = "execId") Long execId) {
        flowExecutorService.retryFailedJobs(execId);
        return FlowResponse.OK;
    }
}
