package com.patterns.factory.abstract_factory.yellowHuman;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:18
 * @Description:
 */
public class YellowFemaleHuman extends AbstractYellowHuman {

    @Override
    public void sex(){
        System.out.println("黄种人的性别为【女】");
    }
}
