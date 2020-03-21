package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.common.constant.ActionConstants;
import com.gsralex.gflow.common.message.GFlowRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gsralex
 * @date 2020/3/1
 */
public class FirstClientHandler extends SimpleChannelInboundHandler<GFlowRequest> {


    @Override

    public void channelActive(ChannelHandlerContext ctx) throws Exception {



        System.out.println("客户端开始写消息...");

        ctx.writeAndFlush(new GFlowRequest().setActionName(ActionConstants.EXECUTE_FLOW));


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GFlowRequest message) throws Exception {
        System.out.println(message);



    }
}
