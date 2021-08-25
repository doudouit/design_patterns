package com.test;

/**
 *
 * （1）基本数据类型传值，对形参的修改不会影响实参；
 * （2）引用类型传引用，形参和实参指向同一个内存地址（同一个对象），所以对参数的修改会影响到实际的对象；
 * （3）String, Integer, Double等immutable的类型特殊处理，可以理解为传值，最后的操作不会修改实参对象。
 *
 */
public class ReferencePkValue1 {
    public static void main(String[] args) {
        ReferencePkValue1 pk = new ReferencePkValue1();
        //String类似基本类型，值传递，不会改变实际参数的值
        String test1 = "Hello";
        pk.change(test1);
        System.out.println(test1);

        //StringBuffer和StringBuilder等是引用传递
        StringBuffer test2 = new StringBuffer("Hello");
        pk.change(test2);

        System.out.println(test2.toString());
    }

    public void change(String str) {
        str = str + "world";
    }

    public void change(StringBuffer str) {
        str.append("world");
    }
}