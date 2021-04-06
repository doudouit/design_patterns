package com.patterns.singleton.ehan1;

/**
 * @Auther: allen
 * @Date: 2020/7/25 09:12
 * @Description: 恶汉式
 *
 * z这样的方式是简单，但是不能延时加载
 */
public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton(){}

    public static Singleton getSingleton() {
        return singleton;
    }
}
