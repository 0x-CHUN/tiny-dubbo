package dubbo.rpc;

import dubbo.annotation.SPI;

/**
 * 消费者进行消费的netty服务器
 */
@SPI("netty")
public interface ConsumerServer {
    Object execute(String address, Request request);
}
