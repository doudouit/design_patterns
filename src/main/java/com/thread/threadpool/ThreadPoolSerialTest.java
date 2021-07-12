package com.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 拒绝策略：
 * 当线程池已经关闭或达到饱和（最大线程和队列都已满）状态时，新提交的任务将会被拒绝。 ThreadPoolExecutor 定义了四种拒绝策略：
 * AbortPolicy：默认策略，在需要拒绝任务时抛出RejectedExecutionException；
 * CallerRunsPolicy：直接在 execute 方法的调用线程中运行被拒绝的任务，如果线程池已经关闭，任务将被丢弃；
 * DiscardPolicy：直接丢弃任务；
 * DiscardOldestPolicy：丢弃队列中等待时间最长的任务，并执行当前提交的任务，如果线程池已经关闭，任务将被丢弃。
 * 我们也可以自定义拒绝策略，只需要实现 RejectedExecutionHandler； 需要注意的是，拒绝策略的运行需要指定线程池和队列的容量。
 *
 * 测试ThreadPoolExecutor对线程的执行顺序
 */
public class ThreadPoolSerialTest {
    public static void main(String[] args) {
        //核心线程数
        int corePoolSize = 3;
        //最大线程数
        int maximumPoolSize = 6;
        //超过 corePoolSize 线程数量的线程最大空闲时间
        long keepAliveTime = 2;
        //以秒为时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
        ThreadPoolExecutor threadPoolExecutor = null;
        try {
            //创建线程池
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    unit,
                    workQueue,
                    new ThreadPoolExecutor.AbortPolicy());

            //循环提交任务
            for (int i = 0; i < 8; i++) {
                //提交任务的索引
                final int index = (i + 1);
                threadPoolExecutor.submit(() -> {
                    //线程打印输出
                    System.out.println("大家好，我是线程：" + index);
                    try {
                        //模拟线程执行时间，10s
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                //每个任务提交后休眠500ms再提交下一个任务，用于保证提交顺序
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}

/**
 * 测试结果分析
 *
 * 首先通过 ThreadPoolExecutor 构造函数创建线程池；
 * 执行 for 循环，提交 8 个任务（恰好等于maximumPoolSize[最大线程数] + capacity[队列大小]）；
 * 通过 threadPoolExecutor.submit 提交 Runnable 接口实现的执行任务；
 * 提交第1个任务时，由于当前线程池中正在执行的任务为 0 ，小于 3（corePoolSize 指定），所以会创建一个线程用来执行提交的任务1；
 * 提交第 2， 3 个任务的时候，由于当前线程池中正在执行的任务数量小于等于 3 （corePoolSize 指定），所以会为每一个提交的任务创建一个线程来执行任务；
 * 当提交第4个任务的时候，由于当前正在执行的任务数量为 3 （因为每个线程任务执行时间为10s，所以提交第4个任务的时候，前面3个线程都还在执行中），此时会将第4个任务存放到 workQueue 队列中等待执行；
 * 由于 workQueue 队列的大小为 2 ，所以该队列中也就只能保存 2 个等待执行的任务，所以第5个任务也会保存到任务队列中；
 * 当提交第6个任务的时候，因为当前线程池正在执行的任务数量为3，workQueue 队列中存储的任务数量也满了，这时会判断当前线程池中正在执行的任务的数量是否小于6（maximumPoolSize指定）；
 * 如果小于 6 ，那么就会新创建一个线程来执行提交的任务 6；
 * 执行第7，8个任务的时候，也要判断当前线程池中正在执行的任务数是否小于6（maximumPoolSize指定），如果小于6，那么也会立即新建线程来执行这些提交的任务；
 * 此时，6个任务都已经提交完毕，那 workQueue 队列中的等待 任务4 和 任务5 什么时候执行呢？
 * 当任务1执行完毕后（10s后），执行任务1的线程并没有被销毁掉，而是获取 workQueue 中的任务4来执行；
 * 当任务2执行完毕后，执行任务2的线程也没有被销毁，而是获取 workQueue 中的任务5来执行；
 */