package com.gsralex.gflow.executor.service;

import com.gsralex.gflow.common.spring.SpringContextUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author niweiwei
 * @date 2020/3/17
 * @Description
 */
public class ExecutorSuit {

    @Autowired
    private FlowExecutorService flowExecutorService;

    public void executeFlow(Long executionId, Map<String, Object> params) throws Exception {
        flowExecutorService.executeFlow(executionId, params);
    }

    @Test
    public void executeFlow() {
        try {
            SpringContextUtils.initialize();
            ExecutorSuit executorSuit = SpringContextUtils.getBean(ExecutorSuit.class);
            executorSuit.executeFlow(1L, null);
        } catch (Exception e) {
            e.getCause();
        }
    }
}