package lesson4;

public class PingPong {

    static Object mon = new Object();
    static volatile int num = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    synchronized (mon) {
                        if (num == 0) {
                            num++;
                        }
                        System.out.print("PING ");
                        mon.notify();
                        mon.wait();
                        Thread.sleep(600);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    synchronized (mon) {
                        if (num == 0) {
                            num++;
                            mon.notify();
                            mon.wait();
                        }
                        System.out.print("PONG ");
                        mon.notify();
                        mon.wait();
                        Thread.sleep(600);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
