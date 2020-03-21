package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.common.spring.SpringContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @date 2020/1/29
 */
public class SchedulerServer {
    
    public SchedulerServer() {
        //TODO:load config
        config = new SchedulerConfig();
    }


    private SchedulerConfig config;

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

        serverBootstrap.bind(config.getPort());
        SpringContextUtils.initialize();
    }

    public static void main(String[] args) {
        SchedulerServer server = new SchedulerServer();
        server.serve();
    }
}
