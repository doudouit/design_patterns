package com.patterns.proxy;

/**
 * 定义西门庆
 * 代理类基于java的多态
 */
public class XiMenQing {
    public static void main(String[] args) {
        // 王婆叫出来
        WangPo wangPo = new WangPo();

        wangPo.makeEyeWithMan();
        wangPo.happyMan();
    }
}
