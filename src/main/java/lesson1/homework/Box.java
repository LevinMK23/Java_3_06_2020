package lesson1.homework;

import java.util.ArrayList;
import java.util.Iterator;

public class Box<T extends Fruit> {

    private ArrayList<T> fruits;
    private float boxWeight = 0f;

    public Box() {
        fruits = new ArrayList<>();
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
        boxWeight += fruit.getWeight();
    }

    public void fillBox(int limit, T fruit) throws CloneNotSupportedException {
        for (int i = 0; i < limit; i++) {
            fruits.add((T) fruit.clone());
        }
    }

    public void dropTo(Box<T> otherBox) {
        boxWeight = 0;

        Iterator<T> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            T fruit = iterator.next();
            otherBox.addFruit(fruit);
            iterator.remove();
        }
    }

    public boolean compare(Box<? extends Fruit> otherBox) {
        return boxWeight == otherBox.boxWeight;
    }
}
