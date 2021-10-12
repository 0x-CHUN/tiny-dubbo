package dubbo.demo;

import dubbo.rpc.annotation.Provider;

@Provider(interfaceClass = Cal.class)
public class Plus implements Cal {
    @Override
    public int cal(int a, int b) {
        return a + b;
    }
}
