package com.patterns.templateMethod.v2;

/**
 * @decription: 抽象模板方法
 * @author: 180449
 * @date 2021/11/19 16:01
 */
public interface AbstractTemplateMethod {

     void start();

     void openDoor();

     void pushElement();

     void closeDoor();

     void end();

    /**
     * 处理的过程
     */
     void handler();
}
