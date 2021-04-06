package com.patterns.factory.factory_method;

/**
 * @Auther: allen
 * @Date: 2020/7/30 15:45
 * @Description: 黑种人
 */
public class BalckHuman implements Human {
    @Override
    public void laugh() {
        System.out.println("黑人笑就牙白");
    }

    @Override
    public void cry() {
        System.out.println("黑人哭了");
    }

    @Override
    public void talk() {
        System.out.println("黑人在聊天");
    }
}
