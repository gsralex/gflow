package com.gsralex.gflow.common.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @author gsralex
 * @date 2020/2/2
 */
public class JsonEncoder extends MessageToByteEncoder<GFlowRequest> {

    private Gson gson = new GsonBuilder().create();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GFlowRequest req, ByteBuf buf) throws Exception {
        buf.writeCharSequence(gson.toJson(req), Charset.forName("utf-8"));
    }
}
