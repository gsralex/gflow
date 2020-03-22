package com.gsralex.gflow.common.spring;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gsralex
 * @date 2020/3/7
 */
public class SpringContextUtils {

    private static ApplicationContext context;

    public static void initialize() {
        context = new AnnotationConfigApplicationContext();
    }

    public static <T> T getBean(Class<T> aClass, Object... args) throws BeansException {
        return context.getBean(aClass, args);
    }
}
