package dubbo.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import dubbo.rpc.ProviderServer;
import dubbo.rpc.annotation.Container;

/**
 * Netty实现的提供者服务器
 */
@Slf4j
public class NettyProviderServer implements ProviderServer {
    @Override
    public void start(String address) {
        Container.registerSelf(address); // 注册服务（即自己能提供哪些服务）
        String[] addrs = address.split(":");
        String ip = addrs[0];
        Integer port = Integer.parseInt(addrs[1]);

        publish(ip, port);
    }

    /**
     * 监听在ip:port处，等待RPC调用
     */
    private void publish(String ip, Integer port) {
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new ObjectEncoder())
                                    .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.cacheDisabled(
                                                    NettyProviderServer.class.getClassLoader()
                                            )))
                                    .addLast(new NettyProviderHandler()); // 进行RPC调用处理的
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            log.info("Server start at " + ip + ":" + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
