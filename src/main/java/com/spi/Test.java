package com.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @decription: 测试
 * @author: 180449
 * @date 2021/9/6 14:50
 */
public class Test {


    /**
     *
     * 可以通过ServiceLoader.load
     * Service.providers方法拿到实现类的实例。
     * Service.providers包位于sun.misc.Service，
     * ServiceLoader.load包位于java.util.ServiceLoader。
     *
     */
    public static void main(String[] args) {
        ServiceLoader<SPIService> load = ServiceLoader.load(SPIService.class);

        Iterator<SPIService> iterator = load.iterator();
        while (iterator.hasNext()) {
            SPIService service = iterator.next();
            service.execute();
        }


        // load.forEach(SPIService::execute);


        String s = "123213213111111111111111111111111111123dfsafafdsafdfdfdfafdaf";
        if (s.length() > 30) {
            System.out.println(s.substring(0, 30));
        } else {
            System.out.println(s);
        }
    }
}
