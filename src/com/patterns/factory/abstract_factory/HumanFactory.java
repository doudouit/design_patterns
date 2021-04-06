package com.patterns.factory.abstract_factory;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:29
 * @Description:
 */
public interface HumanFactory {

    Human createYellowHuman();

    Human createBlackHuman();

    Human createWhiteHuman();
}
