package lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

public class Example3 {
    public static void main(String[] args) {
        AtomicLong l = new AtomicLong();

        CountDownLatch cl = new CountDownLatch(3);
        CyclicBarrier barrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println(finalI + " before");
                    barrier.await();
                    System.out.println(finalI + " after");
                    //System.out.println(finalI);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        cl.countDown();
    }
}
