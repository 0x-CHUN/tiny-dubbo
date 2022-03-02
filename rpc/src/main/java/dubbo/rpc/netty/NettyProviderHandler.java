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
        Request request = (Request) msg; // 获取RPC调用的request
        Object result = new Object();
        log.info(request.toString());
        if (Container.getProviders().containsKey(request.getClassName())) { // 从容器中获取该RPC调用的provider类
            Object provider = Container.getProviders().get(request.getClassName());
            Class<?> providerClass = provider.getClass(); // 获取该类的class名
            Method method = providerClass.getMethod(request.getMethodName(), request.getTypes()); // 获取执行的方法
            result = method.invoke(provider, request.getParams()); // jdk代理！！
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }
}
