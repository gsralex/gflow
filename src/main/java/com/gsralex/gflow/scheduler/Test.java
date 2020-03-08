package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.common.config.GFlowConfig;
import com.gsralex.gflow.common.spring.SpringContextUtils;
import com.gsralex.gflow.scheduler.service.FlowExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @date 2020/3/7
 */
@Component
public class Test {
    @Autowired
    private FlowExecutionService service;

    public void test() {
        service.create("test_flow", null);
    }

    public static void main(String[] args) {
        SpringContextUtils.initialize();
        Test test = SpringContextUtils.getBean(Test.class);
        test.test();
    }
}
