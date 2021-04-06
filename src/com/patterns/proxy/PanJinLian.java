package com.patterns.proxy;

/**
 * 定义潘金莲是怎样的人
 */
public class PanJinLian implements KindWomen{

    @Override
    public void makeEyeWithMan() {
        System.out.println("潘金莲抛媚眼");
    }

    @Override
    public void happyMan() {
        System.out.println("潘金莲在和男人做。。。");
    }
}
