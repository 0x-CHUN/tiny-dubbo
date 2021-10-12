package dubbo.cluster.loadbalancer;

import dubbo.cluster.Loadbalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundLoadBalancer implements Loadbalancer {
    private AtomicInteger previous = new AtomicInteger(0);

    @Override
    public String select(List<String> providers) {
        int size = providers.size();
        if (previous.get() >= size) {
            previous.set(0);
        }
        String provider = providers.get(previous.get());
        previous.incrementAndGet();
        return provider;
    }
}
