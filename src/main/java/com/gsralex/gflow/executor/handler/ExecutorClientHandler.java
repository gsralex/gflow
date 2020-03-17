package com.gsralex.gflow.executor.handler;

import com.gsralex.gflow.common.protocol.GFlowMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author niweiwei
 * @CopyRight (C) http://www.xin.com
 * @email niweiwei@xin.com
 * @date 2020/3/17
 * @Description
 */
public class ExecutorClientHandler extends SimpleChannelInboundHandler<GFlowMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GFlowMessage gFlowMessage) throws Exception {

    }
}
