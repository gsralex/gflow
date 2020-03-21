package com.gsralex.gflow.common.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author gsralex
 * @date 2020/2/2
 */
public class JsonDecoder extends ByteToMessageDecoder {
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        String rawMsg = buf.toString(Charset.forName("utf-8"));
        list.add(gson.fromJson(rawMsg, GFlowRequest.class));
    }
}
