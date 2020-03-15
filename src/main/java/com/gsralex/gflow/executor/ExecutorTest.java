package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.spring.SpringContextUtils;
import com.gsralex.gflow.executor.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/3/7
 */
@Component
public class ExecutorTest {

    @Autowired
    private ExecutorService executorService;

    public void executeFlow(Long executionId, Map<String, Object> params) {
        executorService.executeFlow(executionId, params);
    }

    public static void main(String[] args) {
        SpringContextUtils.initialize();
        ExecutorTest executorTest = SpringContextUtils.getBean(ExecutorTest.class);
        executorTest.executeFlow(1L,null);
    }
}
