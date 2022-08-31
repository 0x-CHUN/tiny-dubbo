package dubbo.rpc;

import dubbo.annotation.SPI;

/**
 * 服务提供者的netty服务器
 */
@SPI("netty")
public interface ProviderServer {
    /**
     * 从address启动服务
     *
     * @param address 启动地址
     */
    void start(String address);
}
