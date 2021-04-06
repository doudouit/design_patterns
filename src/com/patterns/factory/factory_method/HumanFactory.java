package com.patterns.factory.factory_method;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @Auther: allen
 * @Date: 2020/7/30 15:49
 * @Description: 人类制造工厂
 */
public class HumanFactory {

    // 延迟初始化 （Lazy initialization）
    private static HashMap<String,Human> humans = new HashMap<>();

    public static Human createHuman(Class clazz) {
        Human human = null;

        try {
            if (humans.containsKey(clazz.getSimpleName())) {
                human = humans.get(clazz.getSimpleName());
            }else {
                human = (Human) Class.forName(clazz.getName()).newInstance();
                humans.put(clazz.getSimpleName(), human);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("必须指定人种颜色");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("人种定义错误");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("指定的人种找不到");
        }
        return human;
    }

    public static Human createHuman() {
        Human human = null;

        // 首先获得多个实现类，多少个人种
        List<Class> concreteHumanList = ClassUtils.getAllClassByInterface(Human.class);
        Random random = new Random();
        int rand = random.nextInt(concreteHumanList.size());
        human = createHuman(concreteHumanList.get(rand));
        return human;
    }
}
