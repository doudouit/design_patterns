package com.thread.deadthread;

/**
 * 们的线程是交错执行的，那么就很有可能出现以下的情况：
 *
 * 线程A调用leftRight()方法，得到left锁
 * 同时线程B调用rightLeft()方法，得到right锁
 * 线程A和线程B都继续执行，此时线程A需要right锁才能继续往下执行。此时线程B需要left锁才能继续往下执行。
 * 但是：线程A的left锁并没有释放，线程B的right锁也没有释放。
 * 所以他们都只能等待，而这种等待是无期限的-->永久等待-->死锁
 *
 */
public class LeftRightDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        // 得到left锁
        synchronized (left) {
            // 得到right锁
            synchronized (right) {
                doSomething();
            }
        }
    }



    public void rightLeft() {
        // 得到right锁
        synchronized (right) {
            // 得到left锁
            synchronized (left) {
                doSomethingElse();
            }
        }
    }

    private void doSomethingElse() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doSomething() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}