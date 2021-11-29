package com.io.reactor.v1;

/**
 * @decription: 主线程
 * @author: 180449
 * @date 2021/11/29 14:08
 */
public class MainThread {

    public static void main(String[] args) {

        //混杂模式，只有一个线程负责accept，每个都会被分配client，进行R/W
        SelectorThreadGroup stg = new SelectorThreadGroup(3);

        // ，我应该把 监听（9999）的  server  注册到某一个 selector上
        stg.bind(9999);
    }
}
