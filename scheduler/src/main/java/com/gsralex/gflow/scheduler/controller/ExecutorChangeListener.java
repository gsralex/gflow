package com.gsralex.gflow.scheduler.controller;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @date 2020/3/22
 */
@Component
public class ExecutorChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent e) {
        String appName = e.getAppName();
        String serverId = e.getServerId();
        System.out.println(appName);
        System.out.println(serverId);
        System.out.println("EurekaInstanceCanceledEvent");
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent e) {
        InstanceInfo instanceInfo = e.getInstanceInfo();
        System.out.println(instanceInfo);
        System.out.println("EurekaInstanceRegisteredEvent");
    }
}
