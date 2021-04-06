package com.patterns.factory.abstract_factory.whiteHuman;

import com.patterns.factory.abstract_factory.Human;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:14
 * @Description:
 */
public abstract class AbstractWhiteHuman implements Human {

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
