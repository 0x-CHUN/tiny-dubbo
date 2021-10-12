package dubbo.registry;

import dubbo.cluster.Loadbalancer;
import dubbo.cluster.LoadbalancerFactory;
import dubbo.config.Property;

import java.util.List;


public abstract class AbstractRegistrar implements Registrar {
    protected static final String FOLDER = "/dubboregistry";
    protected static final String SEPARATOR = "/";

    public AbstractRegistrar() {
        String address = Property.Registry.address;
        init(address);
    }

    public String discover(String service) {
        List<String> providers = lookup(service);
        Loadbalancer loadbalancer = LoadbalancerFactory.getLoadbalancer();
        return loadbalancer.select(providers);
    }

    /**
     * Initial the dubbo.config
     *
     * @param address the config initial address
     */
    protected abstract void init(String address);

    /**
     * Get the service's providers
     *
     * @param service wanted service
     * @return providers the remote addresses of service
     */
    protected abstract List<String> lookup(String service);

}
