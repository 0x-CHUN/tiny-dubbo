package dubbo.rpc;

import dubbo.loader.ExtensionLoader;
import org.junit.Assert;
import org.junit.Test;

public class ServerTest {
    @Test
    public void ProviderServerSPITest() {
        ProviderServer providerServer = ExtensionLoader.getExtensionLoader(ProviderServer.class)
                .getExtension("netty");
        Assert.assertEquals("dubbo.rpc.netty.NettyProviderServer", providerServer.getClass().getName());
    }

    @Test
    public void ConsumerServerSPITest() {
        ConsumerServer consumerServer = ExtensionLoader.getExtensionLoader(ConsumerServer.class)
                .getExtension("netty");
        Assert.assertEquals("dubbo.rpc.netty.NettyConsumerServer", consumerServer.getClass().getName());
    }

    @Test
    public void ProviderServerTest() {
        ProviderServer providerServer = RpcFactory.getProviderServer();
        providerServer.start("127.0.0.1:20880");
    }

    @Test
    public void ConsumerServerTest() {
        ConsumerServer consumerServer = RpcFactory.getConsumerServer();
        Request request = new Request();
        Object result = consumerServer.execute("127.0.0.1:20880", request);
    }
}