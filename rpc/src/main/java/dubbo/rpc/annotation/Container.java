package dubbo.rpc.annotation;

import dubbo.registry.Registrar;
import dubbo.registry.RegistrarFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Container {
    private static Registrar registrar = RegistrarFactory.getRegistrar();
    private static Map<String, Object> providers = new HashMap<>();

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
