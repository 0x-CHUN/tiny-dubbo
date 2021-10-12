package dubbo.cluster;

import dubbo.annotation.SPI;

import java.util.List;

@SPI("random")
public interface Loadbalancer {
    String select(List<String> providers);
}
