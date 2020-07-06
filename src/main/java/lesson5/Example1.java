package lesson5;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example1 {
    public static void main(String[] args) {
        Semaphore smp = new Semaphore(3);
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lock.lock();
                    for (int j = 0; j < 5; j++) {
                        System.out.print(finalI + 1 + " ");
                        Thread.sleep(500);
                    }
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
