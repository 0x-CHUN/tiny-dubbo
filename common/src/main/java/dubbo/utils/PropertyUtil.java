package dubbo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties的工具类，单例
 */
public class PropertyUtil {
    private volatile static PropertyUtil instance = null;
    private Properties property;

    /**
     * 单例实现
     */
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
            // 获取启动类所在位置的resource文件夹读取application.properties
            in = getClass().getClassLoader().getResourceAsStream("application.properties");
            property = new Properties();
            property.load(in);
        } catch (IOException e) {
            throw new RuntimeException("load application.properties error : ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public String get(String key) {
        return this.property.getProperty(key);
    }
}
