package com.patterns.strategy;

public class BlockEnemy implements IStrategy{
    @Override
    public void operate() {
        System.out.println("孙夫人断后，拦住追兵！");
    }
}
