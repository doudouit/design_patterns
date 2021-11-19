package com.patterns.templateMethod.v2;

/**
 * @decription:
 * @author: 180449
 * @date 2021/11/19 16:07
 */
public class Test {

    public static void main(String[] args) {
        TemplateAServiceImpl templateAService = new TemplateAServiceImpl();
        templateAService.handler();

        TemplateBServiceImpl templateBService = new TemplateBServiceImpl();
        templateBService.handler();
    }
}
