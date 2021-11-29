package com.io.reactor.v3;

/**
 * @decription: 主线程
 * @author: 180449
 * @date 2021/11/29 14:08
 */
public class MainThread {

    public static void main(String[] args) {

        //1,创建 IO Thread  （一个或者多个）
        SelectorThreadGroup boss = new SelectorThreadGroup(3);  //混杂模式
        //boss有自己的线程组

        SelectorThreadGroup worker = new SelectorThreadGroup(3);  //混杂模式

        boss.setWorker(worker);

        // ，我应该把 监听（9999）的  server  注册到某一个 selector上
        boss.bind(9999);
        boss.bind(7777);
        boss.bind(8888);
        boss.bind(6666);

    }
}
