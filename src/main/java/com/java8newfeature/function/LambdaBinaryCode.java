package com.java8newfeature.function;

import java.util.*;

public class LambdaBinaryCode {
    private int lambdaVar = 100;

    public static void main(String[] args) {

        LambdaBinaryCode ins = new LambdaBinaryCode();
        ins.invokeLambda();
        ins.invokeEta();
        ins.invokeLambda2();
    }

    /**
     * 简单的函数式编程示例
     */
    public void invokeLambda() {
        // 准备测试数据
        Integer[] data = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(data);

        // 简单示例：打印List数据
        list.forEach(x -> System.out.println(String.format("Cents into Yuan: %.2f", x / 100.0)));
    }

    /**
     * 简单的函数式编程示例
     */
    public void invokeEta() {
        // 准备测试数据
        Integer[] data = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(data);

        // 通过eta操作符访问
        list.forEach(System.out::println);
    }

    /**
     * 简单的函数式编程示例
     */
    public void invokeLambda2() {
        // 准备测试数据
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int count = 10;
        Random r = new Random();
        while (count-- > 0) {
            map.put(r.nextInt(100), r.nextInt(10000));
        }

        // Lambda调用示例
        map.forEach((x, y) -> {
            System.out.println(String.format("Map key: %1s, value: %2s", x, y + lambdaVar));
        });
    }
}