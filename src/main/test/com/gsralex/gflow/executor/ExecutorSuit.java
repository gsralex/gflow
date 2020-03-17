package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.spring.SpringContextUtils;
import com.gsralex.gflow.executor.service.ExecutorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author niweiwei
 * @date 2020/3/17
 * @Description
 */
@Component
public class ExecutorSuit {
    @Autowired
    private ExecutorService executorService;

    public void executeFlow(Long executionId, Map<String, Object> params) throws Exception {
        executorService.executeFlow(executionId, params);
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
