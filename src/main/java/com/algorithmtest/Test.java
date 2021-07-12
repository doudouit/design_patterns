package com.algorithmtest;

/**
 * @decription: 测试专用
 * @author: 180449
 * @date 2021/3/24 16:06
 */
public class Test {
    public static void main(String[] args) {
        int num = 2;
        System.out.println(bit1Count(3));
    }

    /**
     * 获取一个数字转换为二进制时1的个数
     * @param num
     * @return
     */
    private static int bit1Count(int num) {
        int count = 0;
        // 获取最右侧的1
        while (num != 0) {
            int rightOne = num & (~num + 1);
            count ++;
            num ^= rightOne;
        }
        return count;
    }
}
