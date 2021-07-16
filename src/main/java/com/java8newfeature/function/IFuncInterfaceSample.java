package com.java8newfeature.function;

/**
 * @decription: 函数式接口测试 如何自定义函数式变成
 * @author: 180449
 * @date 2021/7/14 15:50
 */
@FunctionalInterface
public interface IFuncInterfaceSample {
    /*
        约束：
        Each functional interface has a single abstract method,
        1. 每个函数式接口只能有一个抽象方法，多个或没有会报错
        2. 如果抽象方法是覆盖父类的Object方法，也不会计算抽象方法个数，例如 Comparator
        3. @FunctionInterface注解只能作用在接口上，类与枚举enum上不能使用
        4. 只要符合函数式接口约束条件的接口，即使没有采用@FunctionalInterface，编译器都会处理成函数式接口

        结论： Java是通过类来实现函数式编程的

     */


    boolean test1(int i);
//    void test2();
}

class Demo {
    public static void main(String[] args) {
        IFuncInterfaceSample whithout = (x) -> x % 2 == 1;
        System.out.println(whithout.test1(3));
        System.out.println(whithout.test1(4));
    }
}
