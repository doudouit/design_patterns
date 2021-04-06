package com.patterns.classLoadTest;

/**
 * @Auther: allen
 * @Date: 2020/8/7 19:07
 * @Description:
 */
public class Father  {
    int ii;
    int fsi = 4;
    static Son son = new Son();

    {
        System.out.println("4Father init block");
    }

    static {
        System.out.println("5Father static block");
    }

    Father() {
        ii = 1;
        System.out.println("6Father Constructor");
    }
}
