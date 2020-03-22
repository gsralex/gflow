package com.gsralex.gflow.executor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@SpringBootApplication(scanBasePackages = {"com.gsralex.gflow.executor", "com.gsralex.gflow.common"})
@MapperScan("com.gsralex.gflow.*.mapper")
@EnableEurekaServer
@EnableEurekaClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
