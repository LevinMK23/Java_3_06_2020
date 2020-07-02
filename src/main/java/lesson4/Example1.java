package lesson4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example1 {

    static Object mutex = new Object();

    synchronized static void foo() {
        System.out.println(1);
    }

    public static void main(String[] args) throws InterruptedException {
        // ExecutorService service = Executors.newFixedThreadPool(4);
        // Thread.sleep();
        Thread t = new Thread(() -> {
            while (true) {
                synchronized (mutex) {
                    mutex.notify();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
        synchronized (mutex) { // < -
            System.out.println("before");
            mutex.wait(); // notify
            // mutex.notify();
            System.out.println("after");
        }
        //
    }
}
