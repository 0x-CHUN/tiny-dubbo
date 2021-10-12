package dubbo.demo;

import dubbo.rpc.ProviderServer;
import dubbo.rpc.RpcFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
    public static void main(String[] args) {
        ProviderServer providerServer = RpcFactory.getProviderServer();
        providerServer.start("127.0.0.1:20880");
    }
}
