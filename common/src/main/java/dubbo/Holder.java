package dubbo;

/**
 * 持有目标类的对象
 *
 * @param <T> 目标类
 */
public class Holder<T> {
    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }
}
