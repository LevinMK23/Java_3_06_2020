package homework.customTest.module;

import java.lang.reflect.Method;


public class Node {
    private Method method;
    private int level;

    public Node(Method method, int level) {
        this.method = method;
        this.level = level;
    }

    public Method getMethod() {
        return method;
    }

    public int getLevel() {
        return level;
    }
}
