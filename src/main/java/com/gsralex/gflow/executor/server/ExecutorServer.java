package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.common.config.GFlowConfig;
import com.gsralex.gflow.common.exception.CommonException;
import com.gsralex.gflow.common.message.JsonDecoder;
import com.gsralex.gflow.common.message.JsonEncoder;
import com.gsralex.gflow.common.spring.SpringContextUtils;
import com.gsralex.gflow.executor.handler.ExecutorServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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


    public void serve() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new JsonDecoder())
                                    .addLast(new JsonEncoder())
                                    .addLast(SpringContextUtils.getBean(ExecutorServerHandler.class));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(config.getExecutorPort()).sync();
            if (channelFuture.isDone()) {
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new CommonException("executor bind error", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        SpringContextUtils.initialize();
        ExecutorServer server = SpringContextUtils.getBean(ExecutorServer.class);
        server.serve();
    }
}
