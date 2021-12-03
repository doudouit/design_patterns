package com.io.rpcrefactor.rpc;

import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher {

    private static final Dispatcher dis = new Dispatcher();

    private Dispatcher() {
    }

    public static Dispatcher getDis() {
        return dis;
    }

    private static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String k, Object object) {
        invokeMap.put(k, object);
    }

    public Object get(String k) {
        return invokeMap.get(k);
    }
}