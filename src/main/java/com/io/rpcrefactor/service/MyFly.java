package com.io.rpcrefactor.service;

public class MyFly implements Fly {

    @Override
    public void xxoo(String msg) {
        System.out.println("server,get client arg:" + msg);
    }
}