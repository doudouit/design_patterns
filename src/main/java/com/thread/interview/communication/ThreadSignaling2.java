package com.thread.interview.communication;

/**
 *
 * 1 使用synchronized,wait,notify,notifyAll
 *
 * 思路：
 *
 * 在同步方法中先判断信号量，如果不是当前需要的信号使用wait()阻塞线程。
 *
 * 完成打印之后切换信号变量。再唤醒所有线程。
 *
 */
public class ThreadSignaling2 {

    public static void main(String[] args) {
        NorthPrint print = new NorthPrint(new NorthSignal());
        ThreadA threadA = new ThreadA(print);
        ThreadB threadB = new ThreadB(print);
        threadA.start();
        threadB.start();

    }

    public static class ThreadA extends Thread {
        private NorthPrint print;

        public ThreadA(NorthPrint print) {
            this.print = print;
        }

        @Override
        public void run() {
            print.printNumber();

        }
    }

    public static class ThreadB extends Thread {
        private NorthPrint print;

        public ThreadB(NorthPrint print) {
            this.print = print;
        }

        @Override
        public void run() {
            print.printChar();
        }
    }
}