package task3.settings;

public abstract class AbstractSetting<T> {
    protected T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}
