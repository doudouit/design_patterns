package com.thread.blockingqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义blockingQueue
 */
public class AxinBlockQueue {
    //队列容器
    private List<Integer> container = new ArrayList<>();
    private volatile int size;
    private volatile int capacity;
    private Lock lock = new ReentrantLock();
    // Condition
    private final Condition isNull = lock.newCondition();
    private final Condition isFull = lock.newCondition();

    AxinBlockQueue(int cap) {
        this.capacity = cap;
    }

    /**
     * 添加方法
     *
     * @param data
     */
    public void add(int data) {
        lock.lock();
        try {
            try {
                while (size >= capacity) {
                    System.out.println("阻塞队列满了");
                    isFull.await();
                }
            } catch (InterruptedException e) {
                isFull.signal();
                e.printStackTrace();
            }
            ++size;
            container.add(data);
            isNull.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取出元素
     *
     * @return
     */
    public int take() {
        lock.lock();
        try {
            try {
                while (size == 0) {
                    System.out.println("阻塞队列空了");
                    isNull.await();
                }
            } catch (InterruptedException e) {
                isNull.signal();
                e.printStackTrace();
            }
            --size;
            int res = container.get(0);
            container.remove(0);
            isFull.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        AxinBlockQueue queue = new AxinBlockQueue(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.add(i);
                System.out.println("塞入" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (; ; ) {
                System.out.println("消费"+queue.take());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        t1.start();
        t2.start();
    }
}