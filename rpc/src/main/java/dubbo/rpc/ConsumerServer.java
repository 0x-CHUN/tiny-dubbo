package dubbo.rpc;

import dubbo.annotation.SPI;

/**
 * 消费者进行消费的netty服务器
 */
@SPI("netty")
public interface ConsumerServer {
    /**
     * 从address执行特定Request
     *
     * @param address 服务提供者地址
     * @param request 执行的需求
     * @return 响应
     */
    Object execute(String address, Request request);
}
