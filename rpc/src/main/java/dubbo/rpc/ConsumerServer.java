package dubbo.rpc;

import dubbo.annotation.SPI;

@SPI("netty")
public interface ConsumerServer {
    Object execute(String address, Request request);
}
