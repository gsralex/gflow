package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.config.GFlowConfig;
import com.gsralex.gflow.common.spring.SpringContextUtils;
import com.gsralex.gflow.executor.handler.ExecutorHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * @author gsralex
 * @version 2020-01-05
 */
@Component
public class ExecutorServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServer.class);

    @Autowired
    private GFlowConfig config;

    @Autowired
    private ExecutorHandler executorHandler;

    public void serve() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(executorHandler);
                    }
                });
        serverBootstrap.bind(config.getExecutorPort());
    }


    public static void main(String[] args) {
        SpringContextUtils.initialize();
        ExecutorServer server = SpringContextUtils.getBean(ExecutorServer.class);
        server.serve();
    }
}
