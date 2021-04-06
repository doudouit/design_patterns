package com.patterns.factory.abstract_factory.whiteHuman;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:19
 * @Description:
 */
public class WhiteMaleHuman extends AbstractWhiteHuman {
    @Override
    public void sex() {
        System.out.println("该白种人的性别为【男】");
    }
}
