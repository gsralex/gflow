package com.gsralex.gflow.executor.handler;

import com.gsralex.gflow.common.protocol.GFlowMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author niweiwei
 * @date 2020/3/17
 * @Description
 */
public class ExecutorClientHandler extends SimpleChannelInboundHandler<GFlowMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GFlowMessage gFlowMessage) throws Exception {

    }
}
