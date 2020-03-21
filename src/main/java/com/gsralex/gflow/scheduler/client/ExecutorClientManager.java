package com.gsralex.gflow.scheduler.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gsralex
 * @date 2020/3/19
 */
public class ExecutorClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorClientManager.class);

    private static final AtomicInteger SEEK = new AtomicInteger();
    private static Map<InetSocketAddress,ExecutorClient> clients = new ConcurrentHashMap<>();
    private static List<InetSocketAddress> addrs = new ArrayList<>();


    public ExecutorClient getExecutorClient() {
        return clients.get(
                addrs.get(SEEK.getAndIncrement() % addrs.size()));
    }

    public void addClient(InetSocketAddress addr) {
        ExecutorClient client = new ExecutorClient(addr);
        client.addConnectedListener(connected -> {
            if (connected) {
                clients.put(addr, client);
                addrs.add(addr);
            } else {
                LOGGER.warn("addClient error {}", addr);
            }
        });
    }

    public void removeClient(InetSocketAddress addr) {
        clients.remove(addr);
        addrs.remove(addr);
    }

    private ExecutorClientManager() {
    }


    public static ExecutorClientManager getInstance() {
        return ExecutorClientManagerInstance.instance;
    }

    private static class ExecutorClientManagerInstance {
        private static final ExecutorClientManager instance = new ExecutorClientManager();
    }
}
