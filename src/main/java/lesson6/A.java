package lesson6;

import org.apache.log4j.Logger;

import java.util.Collections;

public class A {

    B b;
    Logger LOG = Logger.getLogger(A.class);
    public A() {
        LOG.debug("class A instance was created");
        b = new B();
    }

    @Override
    public String toString() {
        // Collections.synchronizedMap()
        return "A{" +
                "b=" + b +
                '}';
    }

    public void foo1() {
        LOG.debug("text");
        b.foo2();
        // NPE CCE IO AIOB IOB SOF
    }
}
