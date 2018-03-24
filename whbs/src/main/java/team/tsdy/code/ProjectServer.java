package team.tsdy.code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.tsdy.code.net.ActionManager;
import team.tsdy.code.net.netty.NettyServer;
import team.tsdy.code.utils.MysqlConnPool;
import team.tsdy.code.utils.RedisPoolUtil;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class ProjectServer {
    private static final Logger logger = LogManager.getLogger(ProjectServer.class);
    public static void main(String[] args) {
        logger.info("Register Action");
        ActionManager.register();
        logger.info("RedisPoolInit is OK！");
        RedisPoolUtil.initialPool();
        logger.info("MysqlPoolInit is OK！");
        MysqlConnPool.initMysqlPool();
        logger.info("Start Netty Server");
        NettyServer.start();
        logger.info("Server Start OK");
    }
}
