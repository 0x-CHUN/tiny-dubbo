package dubbo.cluster.loadbalancer;

import dubbo.cluster.Loadbalancer;
import dubbo.loader.ExtensionLoader;
import org.junit.Assert;
import org.junit.Test;

public class LoadbalancerTest {
    @Test
    public void SPITest() {
        Loadbalancer round = ExtensionLoader.getExtensionLoader(Loadbalancer.class)
                .getExtension("round");
        Assert.assertEquals("dubbo.cluster.loadbalancer.RoundLoadBalancer", round.getClass().getName());
        Loadbalancer random = ExtensionLoader.getExtensionLoader(Loadbalancer.class)
                .getExtension("random");
        Assert.assertEquals("dubbo.cluster.loadbalancer.RandomLoadbalancer", random.getClass().getName());
    }
}