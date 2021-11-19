package com.patterns.templateMethod.v2;

/**
 * @decription: 模板A的实现类
 * @author: 180449
 * @date 2021/11/19 16:06
 */
public class TemplateAServiceImpl extends AbstractTemplateA{
    @Override
    void start() {
        System.out.println("A ---start");
    }

    @Override
    void openDoor() {
        System.out.println("A ---openDoor");
    }

    @Override
    void pushElement() {
        System.out.println("A ----pushElement");
    }

    @Override
    void closeDoor() {
        System.out.println("A ---closeDoor");
    }

    @Override
    void end() {
        System.out.println("A --end");
    }
}
