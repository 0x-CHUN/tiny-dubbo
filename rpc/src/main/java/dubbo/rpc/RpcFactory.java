package dubbo.rpc;

import dubbo.config.Property;
import dubbo.loader.ExtensionLoader;

/**
 * 获取RPC调用的Consumer和Provider服务器
 */
public class RpcFactory {
    public static ConsumerServer getConsumerServer() {
        String protocol = Property.Rpc.protocol;
        if (protocol == null) {
            protocol = "netty";
        }
        return ExtensionLoader.getExtensionLoader(ConsumerServer.class)
                .getExtension(protocol);
    }

    public static ProviderServer getProviderServer() {
        String protocol = Property.Rpc.protocol;
        if (protocol == null) {
            protocol = "netty";
        }
        return ExtensionLoader.getExtensionLoader(ProviderServer.class).
                getExtension(protocol);
    }
}
