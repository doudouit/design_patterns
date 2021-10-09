package com.thread.interview.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2 Lock,Condition
 */
public class ThreadSignalingReentrant {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try{
                int i = 1;
                while (i <= 26) {
                    System.out.print(i * 2 - 1);
                    System.out.print(i * 2);
                    i++;
                    // 交替执行
                    condition2.signal();
                    condition1.await();
                }
                // 释放另一个线程
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try{
                char i = 'A';
                while (i <= 'Z') {
                    System.out.print(i);
                    i++;
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();
    }
}