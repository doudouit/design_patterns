package com.patterns.strategy;

/**
 * 计谋有了，需要锦囊
 */
public class Context {

    // 构造函数，使用哪个妙计
    private IStrategy iStrategy;

    public Context(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    // 使用计谋了，看我出招
    public void operate() {
        this.iStrategy.operate();
    }
}
