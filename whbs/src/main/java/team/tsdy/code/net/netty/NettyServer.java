package team.tsdy.code.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.tsdy.code.config.ServerConfig;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class NettyServer {
    private static final Logger logger = LogManager.getLogger(NettyServer.class);


    public static void start() {
        new Thread(() -> startNettyServer()).start();
    }

    private static void startNettyServer() {
//        System.setProperty("io.netty.noUnsafe", "true");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//自己加的
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            p.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            p.addLast("idleStateHandler", new IdleStateHandler(ServerConfig.TCP_READ_TIMEOUT, ServerConfig.TCP_WRITE_TIMEOUT, ServerConfig.TCP_READ_TIMEOUT));
                            p.addLast(NettyServerHandler.class.getName(), new NettyServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(ServerConfig.PORT).sync();

            logger.info("NettyServer Listened On : " + ServerConfig.PORT);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
