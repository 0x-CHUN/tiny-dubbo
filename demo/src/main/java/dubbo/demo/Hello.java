package dubbo.demo;

import dubbo.rpc.annotation.Provider;

@Provider(interfaceClass = Welcome.class)
public class Hello implements Welcome {
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}
