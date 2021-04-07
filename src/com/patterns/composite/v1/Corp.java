package com.patterns.composite.v1;

/**
 * 定义一个公司人员抽象类
 */
public abstract class Corp {

    /**
     * 姓名
     */
    private String name = "";

    /**
     * 职位
     */
    private String position = "";

    /**
     * 薪水
     */
    private int salary = 0;

    /**
     * 父节点是谁
     */
    private Corp parent = null;

    public Corp(String name, String position, int salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    //获得员工信息
    public String getInfo(){
        String info = "";
        info = "姓名：" + this.name;
        info = info + "\t职位："+ this.position;
        info = info + "\t薪水：" + this.salary;
        return info;
    }

    //设置父节点
    protected void setParent(Corp _parent){
        this.parent = _parent;
    }

    //得到父节点
    public Corp getParent(){
        return this.parent;
    }
}
