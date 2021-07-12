package com.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @decription: 最近最少使用算法
 * @author: 180449
 * @date 2021/2/25 15:23
 */
public class LRU<K, V> {

    private static final float hashLoadFactory = 0.75f;
    private LinkedHashMap<K, V> map;
    private int cacheSize;

    public LRU(int cacheSize) {
        this.cacheSize = cacheSize;
        int capacity = (int) Math.ceil(cacheSize / hashLoadFactory) + 1;
        map = new LinkedHashMap<K, V>(capacity, hashLoadFactory, true) {
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
//                System.out.println("size:" + size());
//                System.out.println("cacheSize:" + cacheSize);
                return size() > LRU.this.cacheSize;
            }

        };
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized void clear() {
        map.clear();
    }

    public synchronized int usedSize() {
        return map.size();
    }

    public void print() {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.print(entry.getValue() + "--");
        }
        System.out.println();
    }


    public static void main(String[] args) {

        LRU<Integer, String> lru = new LRU<>(3);

        lru.put(1, "a");    // 1:a
        lru.print();
        lru.put(2, "b");    // 2:b 1:a
        lru.print();
        lru.put(3, "c");    // 3:c 2:b 1:a
        lru.print();
        lru.put(4, "d");    // 4:d 3:c 2:b
        lru.print();
        lru.put(1, "aa");   // 1:aa 4:d 3:c
        lru.print();
        lru.put(2, "bb");   // 2:bb 1:aa 4:d
        lru.print();
        lru.put(5, "e");    // 5:e 2:bb 1:aa
        lru.print();
        lru.get(1);         // 1:aa 5:e 2:bb
        lru.print();
        lru.map.remove(11);     // 1:aa 5:e 2:bb
        lru.print();
        lru.map.remove(1);      //5:e 2:bb
        lru.print();
        lru.put(1, "aaa");  //1:aaa 5:e 2:bb
        lru.print();
    }
}
