package dubbo.rpc;

import dubbo.registry.Registrar;
import dubbo.registry.RegistrarFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConsumerProxy {
    public static <T> T create(final Class<T> interfaceClass) {
        Object obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    Request request = new Request();
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParams(args);
                    request.setTypes(method.getParameterTypes());

                    Registrar registrar = RegistrarFactory.getRegistrar();
                    String service = interfaceClass.getName();
                    String address = registrar.discover(service);

                    return RpcFactory.getConsumerServer().execute(address, request);
                });
        return (T) obj;
    }
}
