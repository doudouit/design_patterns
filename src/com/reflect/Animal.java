package com.reflect;

/**
 * @Auther: allen
 * @Date: 2020/8/11 15:59
 * @Description:
 */
public class Animal {
    public String name = "Dog";
    private int age = 30;

    public Animal() {
    }

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //公开 方法  返回类型和参数均有
    public String sayName(String name) {
        return "Hello," + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Animal a1 = new Animal("Dog",10);
        Animal a2 = new Animal("Cat",5);

        Class c1 = a1.getClass();
        Class c2 = a2.getClass();
        System.out.println(c1 == c2);

        Class c3 = Animal.class;
        System.out.println(c1 == c3);


        Class c4 = null;
        try {
            c4 = Class.forName("com.reflect.Animal");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(c1 == c4);
    }
}
