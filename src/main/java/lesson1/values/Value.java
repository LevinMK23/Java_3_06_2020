package lesson1.values;

public class Value<T> {

    T value;

    public Value(T value) {
        this.value = value;
    }

    public T getValueInner() {
        return value;
    }
}
