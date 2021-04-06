package com.patterns.factory.abstract_factory.humanFactory;

import com.patterns.factory.abstract_factory.Human;
import com.patterns.factory.abstract_factory.HumanEnum;
import com.patterns.factory.abstract_factory.HumanFactory;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:31
 * @Description:
 */
public abstract class AbstractHumanFactory implements HumanFactory {

    protected Human createHuman(HumanEnum humanEnum) {
        Human human = null;
        if (!humanEnum.getValue().equals("")) {
            try {
                human = (Human)Class.forName(humanEnum.getValue()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return human;
    }
}
