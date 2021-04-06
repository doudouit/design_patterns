package com.patterns.factory.abstract_factory;

import com.patterns.factory.abstract_factory.humanFactory.FemaleHumanFactory;
import com.patterns.factory.abstract_factory.humanFactory.MaleHumanFactory;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:51
 * @Description:
 */
public class NvWa {

    public static void main(String[] args) {
        HumanFactory maleHumanFactory = new MaleHumanFactory();

        HumanFactory femaleHumanFactory = new FemaleHumanFactory();

        Human maleYellowHuman = maleHumanFactory.createYellowHuman();

        Human femaleYellowHuman = femaleHumanFactory.createYellowHuman();

        maleYellowHuman.cry();
        maleYellowHuman.laugh();
        femaleYellowHuman.sex();
    }
}
