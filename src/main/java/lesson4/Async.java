package lesson4;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Async {

    static AtomicLong result = new AtomicLong();

    public static void main(String[] args) {
        ExecutorService runner = Executors.newFixedThreadPool(4);
        int[] a = new int[100000];
        Arrays.fill(a, 1);
        try {
            for (int i = 0; i < 4; i++) {
                int left = i * 25000, right = left + 25000;
                result.addAndGet(runner.submit(() -> {
                    long sum = 0;
                    for (int j = left; j < right; j++) {
                        sum += a[j];
                    }
                    return sum;
                }).get());
            }
            System.out.println(result.get());
//            Future<String> callResult = runner.submit(() -> {
//                for (int i = 0; i < 5; i++) {
//                    System.out.print((i + 1) + " ");
//                    Thread.sleep(500);
//                }
//                return "OK";
//            });
//            new Thread(() -> {
//                try {
//                    System.out.println("Callable finished work with result: " + callResult.get());
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//            System.out.println("after");
            runner.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
