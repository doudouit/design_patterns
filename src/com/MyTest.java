package com;

import java.util.concurrent.CyclicBarrier;

/**
 * @decription: 测试
 * @author: 180449
 * @date 2021/3/5 14:14
 */
public class MyTest {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {

            }
            System.out.println(1);
        }).start();

        try {
            c.await();
        } catch (Exception e) {

        }
        System.out.println(2);

    }


    /*
    基础数量
    maxpoolsize  线程池最大数量
    poolsize     当前线程数量
    corepoolsize 线程池的基本大小
    queue 等待队列

    1.poolsize<coresize
    2.poolsize>=coresize  queue未满
    3.poolsize>=coresize  queue已满
        3.1  poolsize < maxpoolsize  新增线程
        3.2  poolsize = maxpoolsize  执行拒绝策略，此时不允许创建新的线程
    * */


}
