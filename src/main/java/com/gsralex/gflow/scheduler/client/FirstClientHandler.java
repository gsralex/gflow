package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.common.message.GFlowRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FirstClientHandler extends SimpleChannelInboundHandler<GFlowRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GFlowRequest message) throws Exception {



    }
}
