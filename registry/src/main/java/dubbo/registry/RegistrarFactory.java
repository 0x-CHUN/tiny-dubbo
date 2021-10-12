package dubbo.registry;

import dubbo.config.Property;
import dubbo.loader.ExtensionLoader;

public class RegistrarFactory {
    public static Registrar getRegistrar() {
        String protocol = Property.Registry.protocol;
        return ExtensionLoader.getExtensionLoader(Registrar.class)
                .getExtension(protocol);
    }
}
