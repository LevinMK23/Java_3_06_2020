package lesson7;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Example1 {

    public static void setChartAt(String s, int pos, char ch) throws NoSuchFieldException, IllegalAccessException {
        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);
        char [] data = (char[]) value.get(s);
        data[pos] = ch;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field [] fields = String.class.getDeclaredFields();
        //Arrays.stream(fields).forEach(System.out::println);
        String s = "abcd";
        setChartAt(s, 2, 'O');
        System.out.println(s);
    }
}
