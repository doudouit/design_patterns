package com.patterns.chainOfResponsibility.v3;

/**
 * 订单详情数据分块显示
 *
 * @param <T>
 */
public abstract class AbstractDataHandler<T> {

    /**
     *  处理模块化数据
     *
     * @param query
     * @return
     * @throws Exception
     */
    protected abstract T doRequest(String query) throws Exception;
}