package dubbo.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import dubbo.rpc.ConsumerProxy;
import dubbo.rpc.ConsumerServer;
import dubbo.rpc.Request;

/**
 * Netty实现的消费者服务器
 */
@Slf4j
public class NettyConsumerServer implements ConsumerServer {
    @Override
    public Object execute(String address, Request request) {
        String[] addrs = address.split(":");
        String host = addrs[0];
        Integer port = Integer.parseInt(addrs[1]);
        final NettyConsumerHandler consumerHandler = new NettyConsumerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.cacheDisabled(ConsumerProxy.class.getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(consumerHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(request);
            log.info(request.toString());
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
        return consumerHandler.getResponse();
    }
}
