package dubbo.cluster.loadbalancer;

import dubbo.cluster.Loadbalancer;

import java.util.List;
import java.util.Random;

public class RandomLoadbalancer implements Loadbalancer {
    @Override
    public String select(List<String> providers) {
        int len = providers.size();
        Random random = new Random();
        int idx = random.nextInt(len);
        return providers.get(idx);
    }
}
