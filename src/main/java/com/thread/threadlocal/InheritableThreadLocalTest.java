package com.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @decription: 可继承的ThreadLocal，实现父子线程通信
 * <p>
 * 缺点：
 * 提到并发、多线程就离不开线程池的使用，因为线程池能够复用线程，
 * 减少线程的频繁创建与销毁，如果使用InheritableThreadLocal，
 * 那么线程池中的线程拷贝的数据来自于第一个提交任务的外部线程，
 * 即后面的外部线程向线程池中提交任务时，子线程访问的本地变量都来源于第一个外部线程，造成线程本地变量混乱
 * @author: 180449
 * @date 2021/9/30 16:51
 */
public class InheritableThreadLocalTest {

    /**
     * 模拟tomcat线程池
     */
    private static ExecutorService tomcatExecutors = Executors.newFixedThreadPool(10);
    /**
     * 业务线程池，默认Control中异步任务执行线程池
     */
    private static ExecutorService businessExecutors = Executors.newFixedThreadPool(5);
    /**
     * 线程上下文环境，模拟在Control这一层，设置环境变量，然后在这里提交一个异步任务，模拟在子线程中，是否可以访问到刚设置的环境变量值。
     */
    private static InheritableThreadLocal<Integer> requestIdThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {  // 模式10个请求，每个请求执行ControlThread的逻辑，其具体实现就是，先输出父线程的名称，
            //  然后设置本地环境变量，并将父线程名称传入到子线程中，在子线程中尝试获取在父线程中的设置的环境变量
            tomcatExecutors.submit(new ControlThread(i));
        }

        //简单粗暴的关闭线程池
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        businessExecutors.shutdown();
        tomcatExecutors.shutdown();
    }


    /**
     * 模拟Control任务
     */
    static class ControlThread implements Runnable {
        private int i;

        public ControlThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":" + i);
            requestIdThreadLocal.set(i);
            //使用线程池异步处理任务
            businessExecutors.submit(new BusinessTask(Thread.currentThread().getName()));
        }
    }

    /**
     * 业务任务，主要是模拟在Control控制层，提交任务到线程池执行
     */
    static class BusinessTask implements Runnable {
        private String parentThreadName;

        public BusinessTask(String parentThreadName) {
            this.parentThreadName = parentThreadName;
        }

        @Override
        public void run() {
            //如果与上面的能对应上来，则说明正确，否则失败
            System.out.println("parentThreadName:" + parentThreadName + ":" + requestIdThreadLocal.get());
        }
    }
}
