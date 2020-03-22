package com.gsralex.gflow.executor.controller;

import com.gsralex.gflow.common.entity.api.RestResponse;
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

    @PostMapping("/flow/execute/{execId}")
    public Object executeFlow(@PathVariable(value = "execId") Long execId,
                              Map<String, Object> params) {
        flowExecutorService.executeFlow(execId, params);
        return RestResponse.OK;
    }

    @PutMapping("/flow/pause/{execId}")
    public Object pauseFlow(@PathVariable(value = "execId") Long execId) {
        flowExecutorService.pauseFlow(execId);
        return RestResponse.OK;
    }
}
