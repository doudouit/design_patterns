package com.patterns.factory.abstract_factory.blackHuman;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:19
 * @Description:
 */
public class BlackMaleHuman extends AbstractBalckHuman {
    @Override
    public void sex() {
        System.out.println("该黑种人的性别为【男】");
    }
}
