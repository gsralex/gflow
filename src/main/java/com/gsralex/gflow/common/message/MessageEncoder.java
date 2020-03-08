package com.gsralex.gflow.common.message;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Map;

/**
 * @author gsralex
 * @date 2020/2/2
 */
public class MessageEncoder extends MessageToByteEncoder<GFlowMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GFlowMessage message, ByteBuf buf) throws Exception {
        buf.writeInt(message.getActionName().getBytes().length);
        buf.writeBytes(message.getActionName().getBytes(Charsets.UTF_8));
        buf.writeInt(message.getParams().size());
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            buf.writeInt(entry.getKey().getBytes().length);
//            buf.writeBytes(entry.getKey().getBytes());
//        }
//        return buf;
    }
}
