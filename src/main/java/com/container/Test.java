package com.container;

import java.util.*;

/**
 * @decription:
 * @author: 180449
 * @date 2021/12/20 11:21
 */
public class Test {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        System.out.println(list.size());
        System.out.println(list.get(0));
        System.out.println("-------------------");

        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(null);
        linkedList.add(null);
        System.out.println(linkedList.size());
        System.out.println(linkedList.get(0));
        System.out.println("-------------------");

        HashMap<Object, Object> map = new HashMap<>();
        map.put(null, null);
        map.put(null, null);
        System.out.println(map.size());
        System.out.println(map.get(null));
        System.out.println("-------------------");

        HashSet<Object> hashSet = new HashSet<>();
        hashSet.add(null);
        hashSet.add(null);
        System.out.println(hashSet.size());
        Iterator<Object> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            System.out.println(o);
        }
        System.out.println("-------------------");

        Map<Integer, Integer> map1 = new HashMap<>();
        Integer v1 = map1.put(1, 2);
        System.out.println(v1);
        Integer v2 = map1.put(1, 3);
        System.out.println(v2);
    }
}
