package com.container;

import java.util.Arrays;
import java.util.List;

/**
 * @decription: Arrays测试类
 * @author: 180449
 * @date 2021/3/26 10:07
 */
public class ArraysTest {

    public static void main(String[] args) {
        List<Integer> statusList = Arrays.asList(1, 2);
        System.out.println(statusList);
        System.out.println(statusList.contains(1));
        System.out.println(statusList.contains(3));

//        statusList.add(3);
        System.out.println(statusList.contains(3));

        statusList.remove(1);
        System.out.println(statusList);

    }


}
