package com.patterns.factory.apply;

/**
 * 假设现在leader要你做一个分享商品图片，我们知道商品的类型 有很多，
 * 比如 无SKU 商品，有SKU 商品，下单分享，邀请分享......等一系列的场景。
 * 那我们怎么去设计这个代码做到更加的易懂，易读，今后扩展性好呢？
 *
 * @decription:
 * @author: 180449
 * @date 2021/4/20 15:11
 */
public interface Share {

    /**
     * 获取分享类型
     * @return Integer 枚举
     */
    String getShareFunctionType();

    /**
     * @param shareName
     * @return
     */
    String mainProcess(String shareName);
}
