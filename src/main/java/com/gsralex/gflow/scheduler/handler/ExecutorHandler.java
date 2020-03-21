package com.gsralex.gflow.scheduler.handler;

import com.gsralex.gflow.common.message.GFlowRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class ExecutorHandler extends SimpleChannelInboundHandler<GFlowRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GFlowRequest gFlowRequest) throws Exception {

    }
}
