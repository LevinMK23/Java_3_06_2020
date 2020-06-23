package lesson1.key_points;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Two<String, Long>  two = new Two<>("124", 123L);
        System.out.println(two.getLeft());
        System.out.println(two.getRight());
        ArrayList<Integer> list = new ArrayList<>();
        list.add(13);

        System.out.println(list.get(1));
    }
}
