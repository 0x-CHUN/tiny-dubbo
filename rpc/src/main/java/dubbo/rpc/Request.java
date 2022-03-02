package dubbo.rpc;

import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
public class Request implements Serializable {
    private String className; // 调用的类名
    private String methodName; // 调用的方法名
    private Class[] types; // 参数类型
    private Object[] params; // 参数

    @Override
    public String toString() {
        return "Request{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", types=" + Arrays.toString(types) +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
