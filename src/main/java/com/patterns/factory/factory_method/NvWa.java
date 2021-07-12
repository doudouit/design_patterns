package com.patterns.factory.factory_method;

/**
 * @Auther: allen
 * @Date: 2020/7/30 16:01
 * @Description:
 */
public class NvWa {

    public static void main(String[] args) {

        System.out.println("------第一次造人，没经验--------");
        Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
        whiteHuman.cry();
        whiteHuman.laugh();
        whiteHuman.talk();

        System.out.println("------第二次造人，考大劲儿了--------");
        Human blackHuman = HumanFactory.createHuman(BalckHuman.class);
        blackHuman.cry();
        blackHuman.laugh();
        blackHuman.talk();

        System.out.println("------第三次造人，成功了--------");
        Human yellowHuman = HumanFactory.createHuman(YellowHuman.class);
        yellowHuman.cry();
        yellowHuman.laugh();
        yellowHuman.talk();

        System.out.println("----随机产生-----");
        for (int i = 0; i < 100; i++) {
            Human human = HumanFactory.createHuman();
            human.cry();
            human.talk();
            human.laugh();
        }
    }
}
