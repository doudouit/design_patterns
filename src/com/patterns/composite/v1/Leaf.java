package com.patterns.composite.v1;

/**
 * 普通员工
 */
public class Leaf extends Corp {

    // 就写一个构造函数 必须的
    public Leaf(String name, String position, int salary) {
        super(name, position, salary);
    }
}
