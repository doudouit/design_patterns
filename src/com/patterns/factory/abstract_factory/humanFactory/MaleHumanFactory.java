package com.patterns.factory.abstract_factory.humanFactory;

import com.patterns.factory.abstract_factory.Human;
import com.patterns.factory.abstract_factory.HumanEnum;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:35
 * @Description:
 */
public class MaleHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YellowMaleHuman);
    }

    @Override
    public Human createBlackHuman() {
        return super.createHuman(HumanEnum.BalckMaleHuman);
    }

    @Override
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteMaleHuman);
    }
}
