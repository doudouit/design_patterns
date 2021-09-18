package com.patterns.chainOfResponsibility.v2;

public class ThreeInterview extends Handler {
    @Override
    public void handleRequest(Integer times) {
        if (times == 3) {
            System.out.println("第三次面试"+ times + "，恭喜面试通过，HR会跟你联系！！！");
        }
    }

    public static void main(String[] args) {
        Handler first = new FirstInterview();
        Handler second = new SecondInterview();
        Handler three = new ThreeInterview();
        first.setHandler(second);
        second.setHandler(three);

        // 第一次面试
        first.handleRequest(1);
        System.out.println();
        // 第二次面试
        first.handleRequest(2);
        System.out.println();
        // 第三次面试
        first.handleRequest(3);
        System.out.println();
    }
}