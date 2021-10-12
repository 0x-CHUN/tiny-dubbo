package dubbo.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import dubbo.rpc.Request;
import dubbo.rpc.annotation.Container;

import java.lang.reflect.Method;

@Slf4j
public class NettyProviderHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        Request request = (Request) msg;
        Object result = new Object();
        log.info(request.toString());
        if (Container.getProviders().containsKey(request.getClassName())) {
            Object provider = Container.getProviders().get(request.getClassName());
            Class<?> providerClass = provider.getClass();
            Method method = providerClass.getMethod(request.getMethodName(), request.getTypes());
            result = method.invoke(provider, request.getParams());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }
}
