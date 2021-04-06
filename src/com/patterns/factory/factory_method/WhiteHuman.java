package com.patterns.factory.factory_method;

/**
 * @Auther: allen
 * @Date: 2020/7/30 15:48
 * @Description:
 */
public class WhiteHuman implements Human {
    @Override
    public void laugh() {
        System.out.println("白人会哭");
    }

    @Override
    public void cry() {
        System.out.println("白人会哭");
    }

    @Override
    public void talk() {
        System.out.println("白人很会聊天");
    }
}
