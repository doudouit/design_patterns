package com.patterns.templateMethod.v2;

/**
 * @decription:
 * @author: 180449
 * @date 2021/11/19 16:06
 */
public class TemplateBServiceImpl extends AbstractTemplateB{
    @Override
    public void start() {
        System.out.println("B ---start");
    }

    @Override
    public void openDoor() {
        System.out.println("B ---openDoor");
    }

    @Override
    public void pushElement() {
        System.out.println("B ----pushElement");
    }

    @Override
    public void closeDoor() {
        System.out.println("B ---closeDoor");
    }

    @Override
    public void end() {
        System.out.println("B --end");
    }
}
