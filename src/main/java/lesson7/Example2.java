package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Example2 {


    static Method giveMeRedMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Red.class)) {
                return method;
            }
        }
        return null;
    }


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(void.class);
        System.out.println(int.class);
        String s = "abdceee";
        Class<?> strClass = s.getClass();
//        Arrays.stream(strClass.getDeclaredMethods())
//                .forEach(System.out::println);
        Method substr = strClass.getMethod("substring", int.class, int.class);
        System.out.println(substr);
        // ctrl + shift + /
        String result = (String) substr.invoke(s, 1, 4);
        System.out.println(result);

//        Arrays.stream(Example3.class.getMethods())
//                .forEach(method -> {
//                    if (method.getName().equals("foo1") || method.getName().equals("foo2")) {
//                        System.out.println(method.getName() + ": " + method.isAnnotationPresent(Red.class));
//                        System.out.println(method.getName() + ": " + method.isAnnotationPresent(Green.class));
//                    }
//                });
        giveMeRedMethod(Example3.class).invoke(new Example3());
        // System.out.println(new Example3().foo1());
    }
}
