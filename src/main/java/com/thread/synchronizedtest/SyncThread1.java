package com.thread.synchronizedtest;

public class SyncThread1 implements Runnable {
    private static int count;

    public SyncThread1() {
        count = 0;
    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }

	public static void main(String[] args) {
        method1();
//        method2();
	}

	public static void method1() {
        SyncThread1 syncThread = new SyncThread1();
        Thread thread1 = new Thread(syncThread, "SyncThread1");
        Thread thread2 = new Thread(syncThread, "SyncThread2");
        thread1.start();
        thread2.start();
    }

    public static void method2() {
        Thread thread1 = new Thread(new SyncThread1(), "SyncThread1");
        Thread thread2 = new Thread(new SyncThread1(), "SyncThread2");
        thread1.start();
        thread2.start();
    }
}