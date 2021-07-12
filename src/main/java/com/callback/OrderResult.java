package com.callback;

public interface OrderResult {
    /**
     * 订购货物的状态
     *
     * @param state
     * @return
     */
    //参数可以不用, 用不用按照自己的实际需求决定
    String getOrderResult(String state);
}