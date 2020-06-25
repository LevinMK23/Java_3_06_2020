package lesson1.homework;

public abstract class Fruit implements Cloneable {

    abstract float getWeight();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
