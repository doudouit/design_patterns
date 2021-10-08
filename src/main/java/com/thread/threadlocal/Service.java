package com.thread.threadlocal;

/**
 * 验证ingeritableThreadLocal的特性
 *
 * 子线程可以访问到父线程中的变量
 */
public class Service {
    private static InheritableThreadLocal<Integer> requestIdThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Integer reqId = new Integer(5);
        Service a = new Service();
        a.setRequestId(reqId);
    }

    public void setRequestId(Integer requestId) {
        requestIdThreadLocal.set(requestId);
        doBussiness();
    }

    public void doBussiness() {
        System.out.println("首先打印requestId:" + requestIdThreadLocal.get());
        (new Thread(() -> {
            System.out.println("子线程启动");
            System.out.println("在子线程中访问requestId:" + requestIdThreadLocal.get());
        })).start();
    }
}