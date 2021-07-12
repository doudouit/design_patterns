package com.patterns.factory.abstract_factory.yellowHuman;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:19
 * @Description:
 */
public class YellowMaleHuman extends AbstractYellowHuman {
    @Override
    public void sex() {
        System.out.println("该黄种人的性别为【男】");
    }
}
