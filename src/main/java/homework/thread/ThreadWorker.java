package homework.thread;

import homework.utils.Logger;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadWorker {
    private Logger log = new Logger(ThreadWorker.class);
    private final Object lock = new Object();
    private int t = 0;

    public void thread(String[] values) throws Exception {
        int threadCount = values.length;
        int k = 0;
        AtomicInteger indexLock = new AtomicInteger();

        /*ExecutorService service = Executors.newFixedThreadPool(threadCount);
        while (!Objects.equals(k, 5)) {
            for (String value : values) {
                Callable<String> task = () -> value;
                Future result = service.submit(task);
                System.out.println(result.get());
            }
            k++;
        }
        service.shutdown();*/

        /*for (int i = 0; i < values.length; i++) {
            int index = i;
            new Thread(() -> {
                    try {
                        int j = 0;
                        while (!Objects.equals(j, 5)) {
                            if (Objects.equals(indexLock.get(), index)) {
                                log.appInfo("thread", values[index]);
                                j++;
                                if (Objects.equals(index, values.length - 1)) {
                                    indexLock.set(0);
                                } else {
                                    indexLock.set(index + 1);
                                }
                                Thread.sleep(500);
                            }
                        }
                    } catch (Exception e) {
                        log.appError("thread", "Ошибка работы потока, " + e);
                    }
            }).start();
        }*/

        Thread thread = new Thread(() -> {
            int j = 0;
            while (!Objects.equals(j, 5)) {
                try {
                    synchronized (lock) {
                        if (Objects.equals(t, 0)) {
                            log.appInfo("thread", values[0]);
                            j++;
                            t++;
                            lock.notify();
                            lock.wait();
                            Thread.sleep(500);
                        } else {
                            lock.notify();
                            lock.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            int j = 0;
            while (!Objects.equals(j, 5)) {
                try {
                    synchronized (lock) {
                        if (Objects.equals(t, 1)) {
                            log.appInfo("thread", values[1]);
                            j++;
                            t++;
                            lock.notify();
                            lock.wait();
                            Thread.sleep(500);
                        } else {
                            lock.notify();
                            lock.wait();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            int j = 0;
            while (!Objects.equals(j, 5)) {
                try {
                    synchronized (lock) {
                        if (Objects.equals(t, 2)) {
                            log.appInfo("thread", values[2]);
                            j++;
                            t = 0;
                            lock.notify();
                            lock.wait();
                            Thread.sleep(500);
                        } else {
                            lock.notify();
                            lock.wait();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread2.start();
        thread3.start();

    }
}
