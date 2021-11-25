package com.patterns.templateMethod.v2;

/**
 * @decription: 模板A的实现类
 * @author: 180449
 * @date 2021/11/19 16:06
 */
public class TemplateAServiceImpl extends AbstractTemplateA{
    @Override
    public void start() {
        System.out.println("A ---start");
    }

    @Override
    public void openDoor() {
        System.out.println("A ---openDoor");
    }

    @Override
    public void pushElement() {
        System.out.println("A ----pushElement");
    }

    @Override
    public void closeDoor() {
        System.out.println("A ---closeDoor");
    }

    @Override
    public void end() {
        System.out.println("A --end");
    }
}
