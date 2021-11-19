package com.patterns.templateMethod.v2;

/**
 * @decription:
 * @author: 180449
 * @date 2021/11/19 16:06
 */
public class TemplateBServiceImpl extends AbstractTemplateB{
    @Override
    void start() {
        System.out.println("B ---start");
    }

    @Override
    void openDoor() {
        System.out.println("B ---openDoor");
    }

    @Override
    void pushElement() {
        System.out.println("B ----pushElement");
    }

    @Override
    void closeDoor() {
        System.out.println("B ---closeDoor");
    }

    @Override
    void end() {
        System.out.println("B --end");
    }
}
