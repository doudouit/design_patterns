package com.container;

import java.util.ArrayList;
import java.util.List;

/**
 * 阿里巴巴开发手册
 * 在subList场景中，对元集合进行增删， 均会导致子集合遍历，增加删除产生ConcurrentModificationException;
 *
 * @decription: 测试类
 * @author: 180449
 * @date 2021/3/26 10:14
 */
public class ArrayListTest {

    public static void main(String[] args) {
        List<String> bookList = new ArrayList<>();
        bookList.add("遥远的救世主");
        bookList.add("背叛");
        bookList.add("天幕红尘");
        bookList.add("人生");
        bookList.add("平凡的世界");

        List<String> luyaoBookList = bookList.subList(3, 5);

        System.out.println(bookList);
        System.out.println(luyaoBookList);

        // 修改原集合的值
        bookList.set(3,"路遥-人生");

        System.out.println(bookList);
        System.out.println(luyaoBookList);

        /*// 往原集合中添加元素
        bookList.add("早晨从中午开始");

        System.out.println(bookList);
        System.out.println(luyaoBookList);*/


        // 修改子集合的值, 会影响原集合的值
        luyaoBookList.set(1,"路遥-平凡的世界");

        System.out.println(bookList);
        System.out.println(luyaoBookList);

        // 往子集合中添加元素
        luyaoBookList.add("早晨从中午开始");

        System.out.println(bookList);
        System.out.println(luyaoBookList);
    }
}
