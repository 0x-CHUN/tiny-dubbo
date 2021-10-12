package dubbo.registry.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import dubbo.registry.AbstractRegistrar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ZookeeperRegistrar extends AbstractRegistrar {
    private static final int SESSION_TIMEOUT_MS = 15000;
    private static final int SLEEP_TIMEOUT_MS = 1000;
    private static final int MAX_RETRIES = 3;
    private final Map<String, List<String>> serviceProviderMap = new HashMap<>();

    private CuratorFramework curatorFramework;

    @Override
    public void init(String registerAddress) {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(registerAddress)
                .sessionTimeoutMs(SESSION_TIMEOUT_MS)
                .retryPolicy(new ExponentialBackoffRetry(SLEEP_TIMEOUT_MS, MAX_RETRIES))
                .build();
        curatorFramework.start();
    }

    @Override
    protected List<String> lookup(String service) {
        String path = FOLDER + SEPARATOR + service;
        try {
            List<String> provider = curatorFramework.getChildren().forPath(path);
            serviceProviderMap.put(service, provider);
            watchProvider(path);
            return serviceProviderMap.get(service);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void register(String providerAddress, String service) {
        try {
            String servicePath = FOLDER + SEPARATOR + service;
            Stat stat = curatorFramework.checkExists().forPath(servicePath);
            if (stat == null) {
                curatorFramework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT).forPath(servicePath);
            }
            String provider = servicePath + SEPARATOR + providerAddress;
            curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                    .forPath(provider);
            log.info("Provider:{} is registered to {}", providerAddress, servicePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private void watchProvider(String path) {
        try (CuratorCache cache = CuratorCache.build(curatorFramework, path)) {
            CuratorCacheListener listener = CuratorCacheListener.builder()
                    .forCreates(node -> log.info(String.format("Node created: [%s]", node)))
                    .forChanges((oldNode, node) -> log.info(String.format("Node changed. Old: [%s] New: [%s]", oldNode, node)))
                    .forDeletes(oldNode -> log.info(String.format("Node deleted. Old value: [%s]", oldNode)))
                    .forInitialized(() -> log.info("Cache init"))
                    .build();
            cache.listenable().addListener(listener);
            cache.start();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
