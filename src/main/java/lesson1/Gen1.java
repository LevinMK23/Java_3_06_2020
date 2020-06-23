package lesson1;

public class Gen1<T> {

    T value;

    public Gen1(T value) {
        this.value = value;
    }

    public <E> E getTypeByValue(E value) {
        return value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
