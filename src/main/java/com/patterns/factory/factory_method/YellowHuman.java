package com.patterns.factory.factory_method;

/**
 * @Auther: allen
 * @Date: 2020/7/30 15:43
 * @Description: 黄种人
 */
public class YellowHuman implements Human {

    @Override
    public void laugh() {
        System.out.println("黄种人大笑");
    }

    @Override
    public void cry() {
        System.out.println("黄种人哭");
    }

    @Override
    public void talk() {
        System.out.println("黄种人说话");
    }
}
