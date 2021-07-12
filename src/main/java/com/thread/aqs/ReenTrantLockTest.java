package com.thread.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @decription: AQS测试
 * @author: 180449
 * @date 2021/4/21 16:51
 */
public class ReenTrantLockTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        lock.lock();
        try {
            System.out.println("嘿嘿");
        } finally {
            lock.unlock();
        }
    }
}
