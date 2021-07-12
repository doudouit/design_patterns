package com.patterns.classLoadTest;

/**
 * @Auther: allen
 * @Date: 2020/8/7 19:07
 * @Description:
 */
public class Son extends Father {
    private int i =  1;
    private long l = 2L;
    private static int ssi = 3;

    {
        System.out.println("1Son init block");
    }

    static {
        System.out.println("2Son static block");
    }

    Son() {
        l = 3L;
        System.out.println("3Son Contructor");
    }
}
