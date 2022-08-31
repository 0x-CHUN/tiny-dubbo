package dubbo.registry;

import dubbo.config.Property;
import dubbo.loader.ExtensionLoader;

public class RegistrarFactory {
    public static Registrar getRegistrar() {
        // 读取相应的注册中心的协议类型(Redis、Zookeeper)
        String protocol = Property.Registry.protocol;
        // 根据协议名获取相应的注册中心实例
        return ExtensionLoader.getExtensionLoader(Registrar.class)
                .getExtension(protocol);
    }
}
