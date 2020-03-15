package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.config.GFlowConfig;
import com.gsralex.gflow.common.spring.SpringContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2020-01-05
 */
@Component
public class ExecutorServer {

    @Autowired
    private GFlowConfig config;

    public ExecutorServer() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServer.class);

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
