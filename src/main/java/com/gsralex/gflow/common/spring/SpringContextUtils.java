package com.gsralex.gflow.common.spring;

import com.gsralex.gflow.common.config.GFlowConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gsralex
 * @date 2020/3/7
 */
public class SpringContextUtils {

    private static ApplicationContext context;

    public static void initialize() {
        context = new AnnotationConfigApplicationContext(GFlowConfig.class);
    }

    public static <T> T getBean(Class<T> aClass, Object... args) {
        return context.getBean(aClass, args);
    }
}
