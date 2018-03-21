package team.tsdy.code.service;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.tsdy.code.cache.UserCache;
import team.tsdy.code.model.User;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public static void disconnectFromReaderTimeOut(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();

        ctx.channel().close();

        User user = UserCache.getUserByChannelId(channelId);
        if (user != null) {
            logger.info("[" + user.getUid() + "] 超时断开连接");

        }
    }

    public static void disconnectFromClient(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();

        ctx.channel().close();

        User player = UserCache.getUserByChannelId(channelId);
        if (player != null) {
            logger.info("[" + player.getUid() + "] 主动断开连接");
        }
    }
}
