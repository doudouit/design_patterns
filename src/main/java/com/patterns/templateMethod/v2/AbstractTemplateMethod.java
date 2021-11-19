package com.patterns.templateMethod.v2;

/**
 * @decription: 抽象模板方法
 * @author: 180449
 * @date 2021/11/19 16:01
 */
public abstract class AbstractTemplateMethod {

    abstract void start();

    abstract void openDoor();

    abstract void pushElement();

    abstract void closeDoor();

    abstract void end();

    /**
     * 处理的过程
     */
    abstract void handler();
}
