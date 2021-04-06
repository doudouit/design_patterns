package com.algorithm;

import java.util.*;


public class ConsistentHashingWithVirtualNode {
    /**
     * 待加入Hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111"};

    /**
     * 真实结点列表，考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkList比较好
     */
    private static List<String> realNodes = new LinkedList<>();

    /**
     * 虚拟结点，key表示虚拟结点的hash值，value表示虚拟结点的名称
     */
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * 虚拟结点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟结点
     */
    private static final int VIRTUAL_NODES_NUM = 1000;

    static {
        //先把原始的服务器添加到真实结点列表中
        for (int i = 0; i < servers.length; i++) {
            realNodes.add(servers[i]);
        }
        //在添加虚拟结点，遍历LinkedList使用foreach效率会比较高
        for (String str : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES_NUM; i++) {
                String virtualNodeName = str + "&&VN" + i;
                int hash = getHash(virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    /**
     * 使用FNV1_32_Hash算法计算服务器的hash值，,这里不需要重写hashCode的方法，最终效果没有区别
     */
    public static int getHash(String str) {
        final int p = 167777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 得到应当路由到的结点
     */
    private static String getServer(String node) {
        //得到带路由的结点的hash值
        int hash = getHash(node);
        //得到大于该Hash值的所有map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        Integer i = null;
        String virtualNode = null;
        if (subMap == null || subMap.size() == 0) {
            //取第一个结点
            i = virtualNodes.firstKey();
            virtualNode = virtualNodes.get(i);
        } else {
            i = subMap.firstKey();
            virtualNode = virtualNodes.get(i);
        }
        //返回真实结点地址
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> id = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            id.add(UUID.randomUUID().toString().replace("-", ""));
        }
        for (int i = 0; i < id.size(); i++) {
            String aString = getServer(id.get(i));
            Integer aInteger = map.get(aString);
            if (aInteger == null) {
                map.put(aString, 1);
            } else {
                map.put(aString, aInteger + 1);
            }
            Set<String> set = map.keySet();
            for (String a : set) {
                System.out.println("节点【" + a + "】分配到元素个数为==>" + map.get(a));
            }
        }
    }
}

