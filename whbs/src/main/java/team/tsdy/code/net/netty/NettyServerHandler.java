package team.tsdy.code.net.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.tsdy.code.net.ActionManager;
import team.tsdy.code.service.UserService;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter  {

    private static final Logger logger = LogManager.getLogger(NettyServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            JSONObject json = JSON.parseObject((String) msg);
            logger.info("client say:"+ json.toJSONString());
            ActionManager.submitAction(json, ctx.channel());
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }

    /**
     * 客户端断开触发的函数
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        ctx.close();
    }

    /**
     * 当客户端连接上触发的函数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{

    }

    /**
     * 客户端异常了，触发的函数
     *
     *
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        logger.error("", cause);

        Channel incoming = ctx.channel();
        if (!incoming.isActive()) {
            System.out.println("SimpleClient:" + incoming.remoteAddress()
                    + "异常");
            cause.printStackTrace();
        }
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent t = (IdleStateEvent) evt;
            ctx.channel().id().asLongText();
            switch (t.state()) {
                case ALL_IDLE: {
                    break;
                }
                case READER_IDLE: {
                    UserService.disconnectFromReaderTimeOut(ctx);
                    break;
                }
                case WRITER_IDLE: {
                    break;
                }
                default:
            }
        } else {
            ctx.channel().closeFuture();
        }

    }
}
