package com.gsralex.gflow.scheduler.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author gsralex
 * @date 2020/1/29
 */
class ExecutorClient {


    private InetSocketAddress addr;
    private List<Consumer<Boolean>> connectedConsumers=new ArrayList<>();

    ExecutorClient(InetSocketAddress addr) {
        this.addr = addr;
        initialize();
    }

    private void initialize() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
//                        ch.pipeline().addLast(new FirstClientHandler()¬);
                    }
                });




        bootstrap.connect(addr.getAddress(), addr.getPort()).addListener(future -> {
            if (future.isSuccess()) {
                connectedConsumers.forEach(c->c.accept(true));
            } else {
                connectedConsumers.forEach(c->c.accept(false));
            }
        });
    }

    public void addConnectedListener(Consumer<Boolean> consumer){
        connectedConsumers.add(consumer);
    }
}
