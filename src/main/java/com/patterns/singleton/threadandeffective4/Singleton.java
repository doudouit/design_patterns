package com.patterns.singleton.threadandeffective4;

/**
 * @Auther: allen
 * @Date: 2020/7/25 10:10
 * @Description: 兼顾线程安全与效率的写法
 */
public class Singleton {

    /**
     * volatile 这个关键字有两层语义
     * ① 可见性
     *   一个线程中对该变量进行修改，马上会由工作内存（Working memory）写回主内存（main memory），会马上反应在其他线程的读取操作中
     *   工作内存是独享的，主内存是共享的，他们的关系类似电脑中的高速内存和主存
     * ② 禁止指令重排序化
     *   多线程情况下，编辑器执行的代码顺序可能与我们编写的代码顺序不同，编辑器只保证执行结果与源代码相同
     *
     *   注：volatile关键字只有在jdk1.5之后的版本生效
     */
    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    /**
     * 使用双重检查锁
     * 单例中new的情况非常少，大多数情况都可以进行并行读操作
     * 加锁前加一次null检查，可以减少大多数的加锁操作，提高执行效率
     */
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
