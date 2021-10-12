package dubbo.cluster;

import dubbo.cluster.Loadbalancer;
import dubbo.config.Property;
import dubbo.loader.ExtensionLoader;

public class LoadbalancerFactory {
    public static Loadbalancer getLoadbalancer() {
        String loadbalancer = Property.Cluster.loadbalancer;
        return ExtensionLoader.getExtensionLoader(Loadbalancer.class)
                .getExtension(loadbalancer);
    }
}
