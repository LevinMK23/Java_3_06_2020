package lesson1.key_points;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Example2 {

    public static void foo(ArrayList<? super Number> runner) {
        runner.get(0);
    }

    public static void main(String[] args) {
        // foo(new ArrayIndexOutOfBoundsException(1));
    }
}
