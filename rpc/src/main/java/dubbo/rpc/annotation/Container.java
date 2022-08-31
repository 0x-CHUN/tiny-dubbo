package dubbo.rpc.annotation;

import dubbo.registry.Registrar;
import dubbo.registry.RegistrarFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 一个简单的容器，里面包含一个注册中心
 */
@Slf4j
public class Container {
    private static final Registrar registrar = RegistrarFactory.getRegistrar(); // 注册中心
    private static final Map<String, Object> providers = new HashMap<>(); // 服务名字和对应的provider类

    static {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("")));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Provider.class, true);
        log.info("Provider size : " + classes.size());
        for (Class<?> clazz : classes) {
            try {
                Provider annotation = clazz.getAnnotation(Provider.class);
                Object provider = clazz.newInstance();
                String canonicalName = annotation.interfaceClass().getCanonicalName();
                providers.put(canonicalName, provider);
                log.info(canonicalName);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 将服务注册到注册中心
     *
     * @param address 自己的服务地址
     */
    public static void registerSelf(String address) {
        for (String service : providers.keySet()) {
            registrar.register(address, service);
            log.info(registrar.getClass().getName() + " register " + service);
        }
    }

    public static Map<String, Object> getProviders() {
        return providers;
    }
}
