package com.patterns.singleton.lanhan2;

/**
 * @Auther: allen
 * @Date: 2020/7/25 09:58
 * @Description: 懒汉式
 * 该单例由私有构造器与公有工厂方法够成，可以做到延时加载
 * 缺点：线程不安全
 */
public class Singleton {

    private static Singleton singleton = null;

    private Singleton(){

    }

    public static Singleton getInstance(){
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
