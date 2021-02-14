package lesson1.Homework;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;

public class Box<T extends Fruit> implements Comparable<Box>{
    private LinkedList<Fruit> fruits=new LinkedList<>();
    public float getWeight(){
        float weight=0;
        for(Fruit fruit:fruits)weight+=fruit.getWeight();
        return weight;
    }


    @Override
    public int compareTo(Box o) {
        return (int) (this.getWeight()-o.getWeight());
    }
    public boolean compare(Box b){
        return compareTo(b)==0;
    }
    public void add(@NotNull T fruit){fruits.add(fruit);}
    public Box<T> change(Box<? extends Fruit> box){
        box.fruits.addAll(this.fruits);
        this.fruits.clear();
        return (Box<T>) box;
   }

}
