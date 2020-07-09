package lesson6;

import org.apache.log4j.Logger;

public class B {

    Logger LOG = Logger.getLogger(B.class);

    final static A a = new A();
    public B() {
//        String.format("%d %d %d", 1, 2, 3);
//        LOG.debug("msg: {} ooo: {} asfasf {}", 1, 2, 3);
        LOG.debug("class B instance was created!");
    }

    @Override
    public String toString() {
        return "B{" + a +"}";
    }

    public void foo2() {
        LOG.debug("text");
        // a -> b -> c ..... toString
        // a -> log
        // a.p1, a.p2 -> log
        LOG.debug(new A());
    }
}
