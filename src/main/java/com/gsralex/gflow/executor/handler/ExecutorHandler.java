package com.gsralex.gflow.executor.handler;

import com.gsralex.gflow.common.message.GFlowMessage;
import com.gsralex.gflow.executor.service.ExecutorService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gsralex.gflow.common.constant.ActionConstants.*;

/**
 * @author gsralex
 * @date 2020/2/2
 */
@Service
public class ExecutorHandler extends SimpleChannelInboundHandler<GFlowMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorHandler.class);

    @Autowired
    private ExecutorService executorService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GFlowMessage message) throws Exception {
        switch (message.getActionName()) {
            case EXECUTE_FLOW: {
                Long execId = (Long) message.getParams().get("execId");
                executorService.executeFlow(execId, message.getParams());
                break;
            }
            case PAUSE_FLOW: {
                Long execId = (Long) message.getParams().get("execId");
                executorService.pauseFlow(execId);
                break;
            }
            case STOP_FLOW: {
                Long execId = (Long) message.getParams().get("execId");
                executorService.stopFlow(execId);
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * 异常捕获
     *
     * @param ctx   上下文
     * @param cause 异常对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("server caught exception...", cause);
        ctx.close();
    }
}
