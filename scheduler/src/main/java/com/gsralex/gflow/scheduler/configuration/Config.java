package com.gsralex.gflow.scheduler.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@ComponentScan(basePackages = "com.gsralex.gflow")
@MapperScan(basePackages = "com.gsralex.gflow.common.mapper")
public class Config {
}
