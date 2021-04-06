package com.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 优点：创建一个固定大小线程池，超出的线程会在队列中等待。
 * 缺点：不支持自定义拒绝策略，大小固定，难以扩展
 *
 * @decription: 创建一个固定大小的线程池
 * @author: 180449
 * @date 2021/3/16 15:00
 */
public class FixedThreadPool {

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //打印正在执行的缓存线程信息
                        System.out.println(Thread.currentThread().getName() + "正在被执行");
                        Thread.sleep(2000);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        fixedThreadPool.shutdown();
    }
}
