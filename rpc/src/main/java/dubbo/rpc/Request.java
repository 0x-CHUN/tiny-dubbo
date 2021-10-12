package dubbo.rpc;

import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
public class Request implements Serializable {
    private String className;
    private String methodName;
    private Class[] types;
    private Object[] params;

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
