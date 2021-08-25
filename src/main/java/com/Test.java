package com;

/**
 * @decription:
 * @author: 180449
 * @date 2021/7/28 16:38
 */
public class Test {

    public static void main(String[] args) {
        String str1 = "hello";//字面量 只会在常量池中创建对象
        String str2 = str1.intern();
        System.out.println(str1==str2);//true

        String str3 = new String("world");//new 关键字只会在堆中创建对象
        String str4 = str3.intern();
        System.out.println(str3 == str4);//false

        String str5 = str1 + str2;//变量拼接的字符串，会在常量池中和堆中都创建对象
        String str6 = str5.intern();//这里由于池中已经有对象了，直接返回的是对象本身，也就是堆中的对象
        System.out.println(str5 == str6);//true

        String str7 = "hello1" + "world1";//常量拼接的字符串，只会在常量池中创建对象
        String str8 = str7.intern();
        System.out.println(str7 == str8);//true

        longTest();
    }

    public static void longTest() {
        Long l1 = 1232L;
        Long l2 = 1232L;

        A a = new A();
        a.setA(1232L);

        System.out.println(l1 == l2);
        System.out.println(l1.equals(l2));
        System.out.println(l1 == a.getA());
        System.out.println(l1.equals(a.getA()));
        System.out.println(1232L == a.getA());
    }

    static class A{
        private Long a;

        public Long getA() {
            return a;
        }

        public void setA(Long a) {
            this.a = a;
        }
    }

    static class B{
        private long B;

        public long getB() {
            return B;
        }

        public void setB(long b) {
            B = b;
        }
    }
}
