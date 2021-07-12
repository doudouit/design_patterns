package com.trycatchfinally;

/**
 * @decription: finally测试类
 * @author: 180449
 * @date 2021/4/16 10:31
 */
public class FinallyTest {

    public static void main(String[] args) {
        System.out.println(getValue());
        int k = 0;
        k = k++;
        System.out.println("k：" + k);

        int j = 0;
        j = ++j;
        System.out.println("j：" + j);

        int m = 0;
        int n = 0;

        m = n++;
        System.out.println("m: " + m);
        System.out.println("n: " + n);
    }

    // finally中的代码不执行
    private static int getValue() {
        int num = 0;
        try {
            return num;
        } finally {
            num++;
        }
    }
}
