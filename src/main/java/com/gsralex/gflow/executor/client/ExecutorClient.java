package com.gsralex.gflow.executor.client;

import com.gsralex.gflow.common.protocol.GFlowMessage;
import com.gsralex.gflow.common.protocol.GenericDecoder;
import com.gsralex.gflow.executor.handler.ExecutorClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author gsralex
 * @date 2020/1/29
 */
public class ExecutorClient {

    public void initialize(){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ExecutorClientHandler());
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                .addLast(new GenericDecoder(GFlowMessage.class));
                    }
                });
        // 4.建立连接
        bootstrap.connect("localhost", 80).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.err.println("连接失败!");
            }
        });
    }
}
