package dubbo.config;

import dubbo.utils.PropertyUtil;

public class Property {
    private static String REGISTER_PROTOCOL = "registry.protocol";
    private static String REGISTRY_ADDRESS = "registry.address";
    private static String CLUSTER_LOADBALANCER = "cluster.loadbalancer";
    private static String RPC_PROTOCOL = "rpc.protocol";

    public static class Registry {
        public static String protocol = PropertyUtil.getInstance().get(REGISTER_PROTOCOL);
        public static String address = PropertyUtil.getInstance().get(REGISTRY_ADDRESS);
    }

    public static class Cluster {
        public static String loadbalancer = PropertyUtil.getInstance().get(CLUSTER_LOADBALANCER);
    }

    public static class Rpc {
        public static String protocol = PropertyUtil.getInstance().get(RPC_PROTOCOL);
    }
}
