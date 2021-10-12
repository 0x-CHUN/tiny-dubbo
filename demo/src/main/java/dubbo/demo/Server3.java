package dubbo.demo;

import dubbo.rpc.ProviderServer;
import dubbo.rpc.RpcFactory;

public class Server3 {
    public static void main(String[] args) {
        ProviderServer providerServer = RpcFactory.getProviderServer();
        providerServer.start("127.0.0.1:20882");
    }
}
