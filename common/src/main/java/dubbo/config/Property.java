package dubbo.config;

import dubbo.utils.PropertyUtil;

public class Property {
    private static final String REGISTER_PROTOCOL = "registry.protocol";
    private static final String REGISTRY_ADDRESS = "registry.address";
    private static final String CLUSTER_LOADBALANCER = "cluster.loadbalancer";
    private static final String RPC_PROTOCOL = "rpc.protocol";

    /**
     * 注册中心的配置
     */
    public static class Registry {
        public static String protocol = PropertyUtil.getInstance().get(REGISTER_PROTOCOL);
        public static String address = PropertyUtil.getInstance().get(REGISTRY_ADDRESS);
    }

    /**
     * 负载均衡的配置
     */
    public static class Cluster {
        public static String loadbalancer = PropertyUtil.getInstance().get(CLUSTER_LOADBALANCER);
    }

    /**
     * RPC的配置
     */
    public static class Rpc {
        public static String protocol = PropertyUtil.getInstance().get(RPC_PROTOCOL);
    }
}
