package dubbo.registry;

import dubbo.loader.ExtensionLoader;
import dubbo.registry.redis.RedisRegistrar;
import org.junit.Assert;
import org.junit.Test;
import dubbo.registry.zk.ZookeeperRegistrar;

import java.io.IOException;

public class RegistrarTest {

    @Test
    public void zkTest() {
        ZookeeperRegistrar zkRegistrar = new ZookeeperRegistrar();
        zkRegistrar.init("127.0.0.1:2181");
        zkRegistrar.register("127.0.0.1:20880", "demo.api");
        Assert.assertEquals("127.0.0.1:20880", zkRegistrar.discover("demo.api"));
    }

    @Test
    public void redisTest() {
        RedisRegistrar redisRegistrar = new RedisRegistrar();
        redisRegistrar.init("127.0.0.1:6379");
        redisRegistrar.register("127.0.0.1:20880", "demo.api");
        Assert.assertEquals("127.0.0.1:20880", redisRegistrar.discover("demo.api"));
    }

    @Test
    public void SPITest() {
        Registrar zk = ExtensionLoader.getExtensionLoader(Registrar.class)
                .getExtension("zookeeper");
        Assert.assertEquals("dubbo.registry.zk.ZookeeperRegistrar", zk.getClass().getName());
        Registrar redis = ExtensionLoader.getExtensionLoader(Registrar.class)
                .getExtension("redis");
        Assert.assertEquals("dubbo.registry.redis.RedisRegistrar", redis.getClass().getName());
    }
}