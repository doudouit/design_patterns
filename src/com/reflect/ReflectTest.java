package com.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Auther: allen
 * @Date: 2020/8/11 16:11
 * @Description:
 */
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        Animal animal = new Animal();
        System.out.println(animal.sayName("aa"));
        System.out.println(animal.getName());

        // 1.加载类，指定类的全限定名，包名+类名
        Class c1 = Class.forName("com.reflect.Animal");
        System.out.println(c1);

        // 解刨(反射)类c1的公开构造函数，且参数为null
        Constructor constructor = c1.getConstructor();
        Animal animal1 = (Animal) constructor.newInstance();

        System.out.println(animal1.name);
        
        // 2.b、 解刨(反射)类c1的公开构造函数，参数为string和int
        Constructor cotr2 = c1.getConstructor(String.class, int.class);
        Animal animal2 = (Animal) cotr2.newInstance("cat", 20);

        System.out.println(animal2.name);
        
        // 获取类中的所有字段，包括public private protect 
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        // 获取类中所有的公有字段
        fields = c1.getFields();
        for (Field field : fields) {
            System.out.println(field);

            if (field.getName().equals("name") && field.getType().equals(String.class)){
                String new_name = (String)field.get(animal1);
                System.out.println(new_name);
                new_name  = "哈士奇";
                field.set(animal1,new_name);
            }
        }

        System.out.println(animal1.name);

        // 获取本类所有方法，不包括父类的
        Method[] declaredMethods = c1.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }

        // 获取本类及父类中所有的public方法
        declaredMethods = c1.getMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }

        Method sayName = c1.getMethod("sayName", String.class);
        System.out.println(sayName.invoke(animal1,"haha"));


    }
}
