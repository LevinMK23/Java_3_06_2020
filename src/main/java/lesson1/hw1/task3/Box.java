package lesson1.hw1.task3;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private List<T> fruitList;
    private float boxWeight;

    public Box(List<T> fruitList) {
        this.fruitList = new ArrayList<>();
    }

    public void boxWeight(T fruit) {
        fruitList.add(fruit);
        for (int i = 0; i < fruitList.size(); i++) {
            boxWeight += fruit.getFruitWeight();
        }
    }
    public boolean compareBox(Box box){
        return this.boxWeight == box.boxWeight;
    }

    public void pourFruit(Box<? super T> box) {
        box.fruitList.addAll(this.fruitList);
        fruitList.clear();
    }
}
