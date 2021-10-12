package dubbo.registry;

import dubbo.annotation.SPI;

@SPI("zookeeper")
public interface Registrar {
    /**
     * Register service
     *
     * @param providerAddress the address of provider
     * @param service         the service of provider
     */
    void register(String providerAddress, String service);

    /**
     * Find the service provider's address
     *
     * @param service the wanted service
     * @return the remote address of provider
     */
    String discover(String service);
}
