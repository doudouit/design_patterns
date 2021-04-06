package com.patterns.singleton.threadsafe3;

/**
 * @Auther: allen
 * @Date: 2020/7/25 10:03
 * @Description: 兼顾线程安全的写法
 * <p>
 * 使用volatile关键，确保singleton对象对全部线程是可见的，禁止对其进行指令重排序列化
 */
public class Singleton {

    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        synchronized (Singleton.class) {
            if (singleton == null) {
                singleton = new Singleton();
            }
        }
        return singleton;
    }
}
