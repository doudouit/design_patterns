package com.patterns.composite.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点类
 */
public class Branch extends Corp {
    // 领导下面有哪些下级领导跟小兵
    List<Corp> subordinateList = new ArrayList<>();

    /**
     * 构造函数是必须的
     */
    public Branch(String name, String position, int salary) {
        super(name, position, salary);
    }

    /**
     * 添加一个下属
     * @param corp
     */
    public void addSubordinate(Corp corp) {
        // 设置父节点
        super.setParent(this);
        subordinateList.add(corp);
    }

    /**
     * 看看都有哪些下属
     * @return
     */
    public List<Corp> getSubordinate() {
        return this.subordinateList;
    }

}
