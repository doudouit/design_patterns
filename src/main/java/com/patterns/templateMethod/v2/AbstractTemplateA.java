package com.patterns.templateMethod.v2;

/**
 * @decription: 模板A
 * @author: 180449
 * @date 2021/11/19 16:03
 */
public abstract class AbstractTemplateA implements AbstractTemplateMethod{

    @Override
    public void handler() {
        start();
        openDoor();
        pushElement();
        closeDoor();
        end();
    }
}
