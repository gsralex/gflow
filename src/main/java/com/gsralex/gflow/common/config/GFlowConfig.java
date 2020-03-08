package com.gsralex.gflow.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author gsralex
 * @date 2020/3/7
 */
@Configuration
@ComponentScan("com.gsralex.gflow")
//@PropertySource("classpath:application.properties")
public class GFlowConfig {

    @Value("${datasource.url:jdbc:mysql://sql.w45.vhostgo.com:3306/hntqg}")
    private String url;
    @Value("${datasource.driver-class-name:com.mysql.jdbc.Driver}")
    private String driverClassName;
    @Value("${datasource.username:hntqg}")
    private String username;
    @Value("${datasource.password:X3d8c7j6x99}")
    private String password;

    public String getUrl() {
        return url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
