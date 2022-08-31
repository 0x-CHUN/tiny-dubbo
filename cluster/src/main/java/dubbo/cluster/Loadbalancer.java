package dubbo.cluster;

import dubbo.annotation.SPI;

import java.util.List;

/**
 * 负载均衡器
 */
@SPI("random")
public interface Loadbalancer {
    /**
     * 从服务提供者中选择一个服务提供者的地址
     *
     * @param providers 所有的服务提供者地址
     * @return 服务提供者的地址
     */
    String select(List<String> providers);
}
