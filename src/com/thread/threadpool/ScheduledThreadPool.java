package com.thread.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  优点：创建一个固定大小线程池，可以定时或周期性的执行任务。
 *
 *  缺点：任务是单线程方式执行，一旦一个任务失败其他任务也受影响
 *
 * @decription: 支持定时的定长线程池
 * @author: 180449
 * @date 2021/3/16 15:06
 */
public class ScheduledThreadPool {

    public static void main(String[] args) {
        // 支持定时及周期性任务执行。
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟一秒执行");
            }
        }, 1, TimeUnit.SECONDS);

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟一秒后每三秒执行一次");
                System.out.println(Thread.currentThread().getName());
            }
        }, 1, 3, TimeUnit.SECONDS);

        scheduledThreadPool.shutdown();
    }
}
