package homework.customTest;

import homework.customTest.module.After;
import homework.customTest.module.Before;
import homework.customTest.module.Node;
import homework.customTest.module.Test;
import homework.utils.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TestApp {
    private static Logger log = new Logger(homework.jUnit.Test.class);
    private static Node before;
    private static Node after;
    private static List<Node> tests = new ArrayList<>();

    private void readAnnotations(Class c) {
        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                if ((Objects.isNull(before))) {
                    before = new Node(method, method.getDeclaredAnnotation(Before.class).level());
                } else {
                    log.appError("readAnnotations", "Неверное количество аннотаций Before");
                    throw new RuntimeException("Неверное количество аннотаций Before");
                }
            }
            if (method.isAnnotationPresent(Test.class)) {
                tests.add(new Node(method, method.getDeclaredAnnotation(Test.class).level()));
            }
            if (method.isAnnotationPresent(After.class)) {
                if ((Objects.isNull(after))) {
                    after = new Node(method, method.getDeclaredAnnotation(After.class).level());
                } else {
                    log.appError("readAnnotations", "Неверное количество аннотаций After");
                    throw new RuntimeException("Неверное количество аннотаций After");
                }
            }
        }
        tests.sort((o1, o2) -> o2.getLevel() - o1.getLevel());
        log.appInfo("readTests", "Параметры считаны");
    }

    private static void invokeClass(Class<?> clazz) throws Exception {
        new TestApp().readAnnotations(clazz);
        if (!Objects.isNull(before) && !Objects.isNull(after)) {
            log.appInfo("invokeClass", "Выполняю операции Before");
            if (!Objects.isNull(before)) {
                before.getMethod().invoke(clazz.newInstance());
            }
            log.appInfo("invokeClass", "Выполняю операции Test");
            if (!tests.isEmpty()) {
                for (Node m : tests) {
                    m.getMethod().invoke(clazz.newInstance());
                }
            }
            log.appInfo("invokeClass", "Выполняю операции After");
            if (!Objects.isNull(after)) {
                after.getMethod().invoke(clazz.newInstance());
            }
            log.appInfo("invokeClass", "Все операции пройдены");
        } else {
            log.appError("invokeClass", "Некорректные параметры класса");
            throw new RuntimeException("Отсутсвуют аннотации After или Before");
        }
    }

    public static void main(String[] args) throws Exception {
        invokeClass(TestWorker.class);
    }
}
