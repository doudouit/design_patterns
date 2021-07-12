package com.patterns.factory.abstract_factory.humanFactory;

import com.patterns.factory.abstract_factory.Human;
import com.patterns.factory.abstract_factory.HumanEnum;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:39
 * @Description:
 */
public class FemaleHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YellowFemaleHuman);
    }

    @Override
    public Human createBlackHuman() {
        return super.createHuman(HumanEnum.BalckFemaleHuman);
    }

    @Override
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteFemaleHuman);
    }
}
