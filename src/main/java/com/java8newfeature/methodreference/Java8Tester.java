package com.java8newfeature.methodreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 方法引用示例
 */
public class Java8Tester {
    public static void main(String args[]) {
        List<String> names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");

        names.forEach(System.out::println);

        // 构造器引用：它的语法是Class::new
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        cars.forEach(System.out::println);

        // 静态方法引用：它的语法是Class::static_method
        cars.forEach(Car::collide);

        // 特定类的任意对象的方法引用
        cars.forEach(Car::repair);

        //  特定对象的方法引用：它的语法是instance::method实例如下
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }
}