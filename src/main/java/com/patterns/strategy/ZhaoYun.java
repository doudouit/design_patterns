package com.patterns.strategy;

public class ZhaoYun {

    /**
     *  策略模式的好处 高内聚，低耦合，符合OCP原则（对接口开发，对修改关闭）
     *
     * 赵云出场了，根据诸葛亮的交代，依次拆开锦囊
     * @param args
     */
    public static void main(String[] args) {
        Context context;

        System.out.println("------刚刚来到吴国，拆开第一个锦囊-----");
        context = new Context(new BackDoor()); // 拿到妙计
        context.operate();// 拆开执行
        System.out.println("\n\n\n\n\n\n");

        System.out.println("------刘备乐不思蜀了，拆开第二个锦囊-----");
        context = new Context(new GrvenGreenLight()); // 拿到妙计
        context.operate();// 拆开执行
        System.out.println("\n\n\n\n\n\n");

        System.out.println("------孙权的小兵来了，拆开第三个锦囊-----");
        context = new Context(new BackDoor()); // 拿到妙计
        context.operate();// 拆开执行
        System.out.println("\n\n\n\n\n\n");
    }
}
