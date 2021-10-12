package dubbo.rpc;

import dubbo.annotation.SPI;

@SPI("netty")
public interface ProviderServer {
    void start(String address);
}
