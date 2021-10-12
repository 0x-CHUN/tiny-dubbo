package dubbo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private volatile static PropertyUtil instance = null;
    private Properties property;

    public static PropertyUtil getInstance() {
        if (instance == null) {
            synchronized (PropertyUtil.class) {
                if (instance == null) {
                    instance = new PropertyUtil();
                }
            }
        }
        return instance;
    }

    public PropertyUtil() {
        init();
    }

    private void init() {
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream("application.properties");
            property = new Properties();
            property.load(in);
        } catch (IOException e) {
            throw new RuntimeException("load application.properties error : ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException("close application.properties error : ", e);
                }
            }
        }
    }

    public String get(String key) {
        return this.property.getProperty(key);
    }
}
