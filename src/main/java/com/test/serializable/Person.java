package com.test.serializable;

import java.io.Serializable;

/**
 * @decription: person
 * @author: 180449
 * @date 2021/8/26 15:30
 */
public class Person implements Serializable {

    private static final long serialVersionUID = -1853786690258047403L;

    private String name;

    private Integer age;


    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
