package lesson1.hw1.task1;

import java.util.ArrayList;
import java.util.List;

public class Main <T> {

    public static <T> void changeEl(List<T> list){
        T firstElement = list.get(0);
        T lastElement = list.get(list.size() - 1);
        list.set(0, lastElement);
        list.set(list.size() - 1, firstElement);
    }

    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();

        l.add(0, 1);
        l.add(1, 2);
        l.add(2, 3);
        l.add(3, 4);

        System.out.println(l);

        changeEl(l);
        System.out.println(l);
    }
}
