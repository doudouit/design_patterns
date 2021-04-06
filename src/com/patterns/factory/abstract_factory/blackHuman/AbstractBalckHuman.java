package com.patterns.factory.abstract_factory.blackHuman;

import com.patterns.factory.abstract_factory.Human;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:14
 * @Description:
 */
public abstract class AbstractBalckHuman implements Human {

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
