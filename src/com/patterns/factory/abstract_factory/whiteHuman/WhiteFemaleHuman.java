package com.patterns.factory.abstract_factory.whiteHuman;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:18
 * @Description:
 */
public class WhiteFemaleHuman extends AbstractWhiteHuman {

    @Override
    public void sex(){
        System.out.println("白种人的性别为【女】");
    }
}
