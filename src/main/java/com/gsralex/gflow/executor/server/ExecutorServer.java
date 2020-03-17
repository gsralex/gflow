package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.common.config.GFlowConfig;
import com.gsralex.gflow.common.protocol.GFlowMessage;
import com.gsralex.gflow.common.protocol.GenericDecoder;
import com.gsralex.gflow.common.spring.SpringContextUtils;
import com.gsralex.gflow.executor.handler.ExecutorServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServer.class);

    @Autowired
    private GFlowConfig config;

    @Autowired
    private ExecutorServerHandler executorServerHandler;

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
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                .addLast(new GenericDecoder(GFlowMessage.class));
                        ch.pipeline().addLast(executorServerHandler);
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
