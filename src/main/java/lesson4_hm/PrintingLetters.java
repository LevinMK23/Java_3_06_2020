package lesson4_hm;

public class PrintingLetters {
    byte counter=0;
    void print(){
        Thread first,second,third;
        Object mon=new Object();
         first=new Thread(()->{
            int g=0;
            while (g<5){
               synchronized (mon){
                   mon.notifyAll();
                   while (counter!=0) {
                       try {
                           mon.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                   System.out.print("a");
                   g++;
                   counter=1;
               }
           }
        });
        first.start();
         second =new Thread(()->{
            int g=0;
            while (g<5){
                synchronized (mon){
                    mon.notifyAll();
                    while (counter!=1) {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("b");
                    counter=2;
                    if (!first.isAlive())mon.notify();

                    g++;
                }
            }
        });
        second.start();
         third =new Thread(()->{
            int g=0;
            while (g<5){
               // System.out.println("thread 3 waits");
                synchronized (mon){
                    //System.out.println("thread 3 no waits");
                    mon.notifyAll();
                    while (counter!=2) {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("c");
                    g++;
                    counter=0;
                    if (!second.isAlive()||!first.isAlive())mon.notify();
                }
            }
        });
        third.start();
    }
}
