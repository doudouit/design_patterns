package com.thread.synchronizedtest;

/**
 * @decription: syncronized测试
 * @author: 180449
 * @date 2021/9/27 14:30
 */
public class SynchronizedTest {

    public synchronized void test1() {
        System.out.println("i am acc");
    }

    public void test2() {
        synchronized (this) {

        }
    }


    // 锁粗化
    public String test() {
        int i = 0;
        StringBuffer sb = new StringBuffer();
        while (i < 100) {
            sb.append("a");
            i++;
        }
        return sb.toString();
    }

}
