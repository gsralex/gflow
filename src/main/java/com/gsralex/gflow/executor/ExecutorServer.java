package com.gsralex.gflow.executor;

import com.gsralex.gflow.common.spring.SpringContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2020-01-05
 */
public class ExecutorServer {
    public ExecutorServer(){
    }

    private static final Logger LOGGER= LoggerFactory.getLogger(ExecutorServer.class);

    public void serve(int port){
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
        serverBootstrap.bind(port);
        SpringContextUtils.initialize();
    }


    public static void main(String[] args){
        ExecutorServer server=new ExecutorServer();
        server.serve(8080);

    }
}
