package lesson5;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

public class Example2 implements Runnable {

    private int cnt = 0;
    private static int consistentCounter = 0;
    private ExecutorService service;
    private Lock lock;

    public Example2(int cnt, Lock lock) {
        this.cnt = cnt;
        service = Executors.newFixedThreadPool(2);
        this.lock = lock;
    }

    public void hardCalculation() {
        System.out.println("Hard calculation started!");
        File file = new File("./file" + cnt + ".txt");
        try (FileOutputStream os = new FileOutputStream(file)) {
            for (int i = 0; i < 10000000; i++) {
                os.write((byte) i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Hard calculation finished!");
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            System.out.print(cnt + " ");
            lock.lock();
            consistentCounter++;
            Thread.sleep(300);
            if (consistentCounter % 20 == 0) {
                consistentCounter = 0;
                lock.unlock();
                service.execute(this::hardCalculation);
            } else {
                lock.unlock();
            }
        }
        //service.shutdown();
    }
}
