package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class Example4 {

    static class Node {
        Method method;
        int level;

        public Node(Method method, int level) {
            this.method = method;
            this.level = level;
        }
    }

    static void invokeTheBiggestLevel(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Example3 inst = new Example3();
        ArrayList<Node> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Red.class)) {
                methods.add(new Node(method, method.getDeclaredAnnotation(Red.class).level()));
            }
            if (method.isAnnotationPresent(Green.class)) {
                methods.add(new Node(method, method.getDeclaredAnnotation(Green.class).level()));
            }
        }
        methods.sort((o1, o2) -> o2.level - o1.level);
        if (!methods.isEmpty()) {
            methods.get(0).method.invoke(inst);
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        invokeTheBiggestLevel(Example3.class);
    }
}
