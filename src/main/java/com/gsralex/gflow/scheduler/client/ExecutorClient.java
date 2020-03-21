package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.common.constant.ActionConstants;
import com.gsralex.gflow.common.message.GFlowRequest;
import com.gsralex.gflow.common.message.JsonDecoder;
import com.gsralex.gflow.common.message.JsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author gsralex
 * @date 2020/1/29
 */
class ExecutorClient {
    private InetSocketAddress addr;
    private List<Consumer<Boolean>> connectedConsumers = new ArrayList<>();

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
                        ch.pipeline()
                                .addLast(new JsonDecoder())
                                .addLast(new JsonEncoder())
                               //.addLast(new FirstClientHandler())
                        ;
                    }
                });

        try {
            ChannelFuture f = bootstrap.connect(addr.getAddress(), addr.getPort()).addListener(future -> {
                if (future.isSuccess()) {
                    connectedConsumers.forEach(c -> c.accept(true));
                } else {
                    connectedConsumers.forEach(c -> c.accept(false));
                }
            }).sync();
            f.channel().writeAndFlush(new GFlowRequest().setActionName(ActionConstants.EXECUTE_FLOW));
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }


    public void addConnectedListener(Consumer<Boolean> consumer) {
        connectedConsumers.add(consumer);
    }

    public static void main(String[] args) {
        ExecutorClient client = new ExecutorClient(new InetSocketAddress(8081));
        System.out.println("test client");
    }
}
