package com.patterns.singleton.enum6;

/**
 * @Auther: allen
 * @Date: 2020/7/25 10:39
 * @Description: 使用枚举类保证线程安全、防止反射强行调用构造器、还有自动序列化机制，防止发序列化的时候创建新的对象
 */
public enum Singleton {
    INSTANCE;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
