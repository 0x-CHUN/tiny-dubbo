package dubbo.rpc;

import dubbo.registry.Registrar;
import dubbo.registry.RegistrarFactory;

import java.lang.reflect.Proxy;

/**
 * 消费者的代理
 */
public class ConsumerProxy {
    /**
     * @param interfaceClass 消费者需要代理的类
     * @param <T>            某个interface
     * @return 可以执行RPC调用的代理类
     */
    public static <T> T create(final Class<T> interfaceClass) {
        Object obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    Request request = new Request();
                    request.setClassName(method.getDeclaringClass().getName()); // 获取类名
                    request.setMethodName(method.getName()); // 获取方法名
                    request.setParams(args); // 获取参数
                    request.setTypes(method.getParameterTypes());

                    Registrar registrar = RegistrarFactory.getRegistrar(); // 获取注册中心
                    String service = interfaceClass.getName(); // 获取类名
                    String address = registrar.discover(service); // 进行服务发现得到服务地址

                    return RpcFactory.getConsumerServer().execute(address, request); // 从RPC工厂中获取消费服务器，执行RPC调用
                });
        return (T) obj;
    }
}
