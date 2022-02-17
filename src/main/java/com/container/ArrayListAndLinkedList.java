package com.container;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @decription: arrayLIst 与 linkedList对比
 * @author: 180449
 * @date 2021/12/20 10:30
 */
public class ArrayListAndLinkedList {

    public static void main(String[] args) {
        insertArrayListSpeed();
        insertLinkedListSpeed();
    }

    private static final int COUNT = 1_0000_0000;

    public static void insertArrayListSpeed() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("arrayList插入" + COUNT + "条数据耗时：" + (endTime - startTime));
    }

    public static void insertLinkedListSpeed() {
        long startTime = System.currentTimeMillis();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < COUNT; i++) {
            linkedList.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("LinkedList插入" + COUNT + "条数据耗时：" + (endTime - startTime));
    }
}
