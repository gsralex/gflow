package com.gsralex.gflow.scheduler.handler;

import com.gsralex.gflow.common.message.GFlowMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class ExecutorHandler extends SimpleChannelInboundHandler<GFlowMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GFlowMessage gFlowMessage) throws Exception {

    }
}
