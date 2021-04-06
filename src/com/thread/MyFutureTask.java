package com.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现 Callable 接口，通过 FutureTask 包装器来创建 Thread 线程有返回值
 *
 * @decription: 通过FutureTask创建线程
 * @author: 180449
 * @date 2021/3/20 10:05
 */
public class MyFutureTask {
    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask<>(
                () -> {
                    return 5;
                });

        new Thread(task, "有返回值的线程").start();

        try {
            task.get();
            System.out.println("子线程返回值：" + task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
