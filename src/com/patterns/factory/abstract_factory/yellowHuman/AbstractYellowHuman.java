package com.patterns.factory.abstract_factory.yellowHuman;

import com.patterns.factory.abstract_factory.Human;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:10
 * @Description:
 */
public abstract class AbstractYellowHuman implements Human {

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
