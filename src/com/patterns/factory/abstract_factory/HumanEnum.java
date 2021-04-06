package com.patterns.factory.abstract_factory;

/**
 * @Auther: allen
 * @Date: 2020/7/30 17:23
 * @Description:
 */
public enum HumanEnum {
    YellowMaleHuman("com.patterns.factory.abstract_factory.yellowHuman.YellowMaleHuman"),
    YellowFemaleHuman("com.patterns.factory.abstract_factory.yellowHuman.YellowFemaleHuman"),
    BalckMaleHuman("com.patterns.factory.abstract_factory.blackHuman.BlackMaleHuman"),
    BalckFemaleHuman("com.patterns.factory.abstract_factory.blackHuman.BlackFemaleHuman"),
    WhiteMaleHuman("com.patterns.factory.abstract_factory.whiteHuman.WhiteMaleHuman-"),
    WhiteFemaleHuman("com.patterns.factory.abstract_factory.whiteHuman.WhiteFemaleHuman"),
    ;

    private String value = "";

    public String getValue() {
        return value;
    }

    private HumanEnum(String value) {
        this.value = value;
    }
}
