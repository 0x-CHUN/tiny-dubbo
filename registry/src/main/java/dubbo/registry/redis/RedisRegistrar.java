package dubbo.registry.redis;

import dubbo.registry.AbstractRegistrar;

import java.util.List;

public class RedisRegistrar extends AbstractRegistrar {
    @Override
    public void init(String address) {
        RedisUtils.init(address);
    }

    @Override
    protected List<String> lookup(String service) {
        return RedisUtils.getJedis().lrange(FOLDER + SEPARATOR + service, 0, -1);
    }

    @Override
    public void register(String providerAddress, String service) {
        RedisUtils.getJedis().lpush(FOLDER + SEPARATOR + service, providerAddress);
    }
}
