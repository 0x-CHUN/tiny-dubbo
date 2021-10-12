package dubbo.demo;

import dubbo.rpc.ConsumerProxy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) {
        Welcome welcome = ConsumerProxy.create(Welcome.class);
        log.info(welcome.greet("CCCC"));
        Cal cal = ConsumerProxy.create(Cal.class);
        log.info("Result : " + cal.cal(1, 2));
    }
}
