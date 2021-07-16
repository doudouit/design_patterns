package com.thread.synchronizedtest;

public class ClassThread implements Runnable {
    private static int count;

    public ClassThread() {
        count = 0;
    }

    public static void method() {
        synchronized(SyncThread.class) {
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public synchronized void run() {
        method();
    }

    public static void main(String[] args) {
        ClassThread classThread1 = new ClassThread();
        ClassThread classThread2 = new ClassThread();
        Thread thread1 = new Thread(classThread1, "classThread1");
        Thread thread2 = new Thread(classThread2, "classThread2");
        thread1.start();
        thread2.start();
    }
}