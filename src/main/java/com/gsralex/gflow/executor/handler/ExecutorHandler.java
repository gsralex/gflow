package com.gsralex.gflow.executor.handler;

import com.gsralex.gflow.common.FlowAction;
import com.gsralex.gflow.common.message.GFlowMessage;
import com.gsralex.gflow.common.request.CreateFlowRequest;
import com.gsralex.gflow.executor.service.ExecutorService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

import static com.gsralex.gflow.common.constant.ActionConstants.ACTION_EXECUTE_FLOW;

/**
 * @author gsralex
 * @date 2020/2/2
 */
public class ExecutorHandler extends SimpleChannelInboundHandler<FlowAction> {

    private ExecutorService executorService;

    public ExecutorHandler() {
        executorService = new ExecutorService();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FlowAction flowAction) throws Exception {
        String action = StringUtils.lowerCase(flowAction.getAction());
        if (ACTION_EXECUTE_FLOW.equals(action)) {
            CreateFlowRequest request = (CreateFlowRequest) flowAction.getParam();
            executorService.executeFlow(request.getExecutionId(), request.getParams());
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
//        LOGGER.error("server caught exception...", cause);
        ctx.close();
    }
}
