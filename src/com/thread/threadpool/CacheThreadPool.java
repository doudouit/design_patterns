package com.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors.newCachedThreadPool()：可缓存线程池，先查看池中有没有以前建立的线程，如果有，就直接使用。如果没有，就建一个新的线程加入池中。
 * 可缓存线程池为无限大，当执行当前任务时上一个任务已经完成，会复用执行上一个任务的线程，而不是每次新建线程。
 * 终止并从缓存中移除那些已有 60 秒钟未被使用的线程。可缓存线程池通常用于执行一些生存期很短的异步型任务。
 *
 * 缺点：一但线程无线增值，会导致内存溢出
 *
 * @decription: 可缓存线程池
 * @author: 180449
 * @date 2021/3/16 14:46
 */
public class CacheThreadPool {

    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            try {
                //sleep可明显看到使用的是线程池里面以前的线程，没有创建新的线程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //打印正在执行的缓存线程信息
                    System.out.println(Thread.currentThread().getName() + "正在被执行");
                }
            });
            
        }

        cachedThreadPool.shutdown();
    }
}
