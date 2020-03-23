package com.gsralex.gflow.scheduler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.gsralex.gflow.scheduler", "com.gsralex.gflow.common"})
@MapperScan("com.gsralex.gflow.common.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
