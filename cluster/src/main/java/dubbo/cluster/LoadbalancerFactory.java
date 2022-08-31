package dubbo.cluster;

import dubbo.cluster.Loadbalancer;
import dubbo.config.Property;
import dubbo.loader.ExtensionLoader;

/**
 * 根据配置加载某个特定的负载均衡器
 */
public class LoadbalancerFactory {
    public static Loadbalancer getLoadbalancer() {
        String loadbalancer = Property.Cluster.loadbalancer;
        return ExtensionLoader.getExtensionLoader(Loadbalancer.class)
                .getExtension(loadbalancer);
    }
}
