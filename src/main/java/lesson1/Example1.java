package lesson1;

import java.util.ArrayList;

public class Example1 {
    public static void main(String[] args) {
        Object o1 = "123";
        Object o2 = 1.5;
        Object o3 = new ArrayList<>();
        //int x = (int) o2;
        System.out.println(o1.getClass());
        System.out.println(o2.getClass());
        System.out.println(o3.getClass());
        if (o2 instanceof String) {
            System.out.println(((String) o2).replaceAll("2", ""));
        } else if (o2 instanceof Double){
            System.out.println(((Double) o2).longValue());
        } else {
            System.out.println(((ArrayList) o2).size());
        }
        System.out.println(double.class);
    }
}
