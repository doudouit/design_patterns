package com;

import java.lang.reflect.Field;

/**
 * @decription: String类型测试
 * @author: 180449
 * @date 2021/7/28 17:16
 */
public class StringTest {

    public static void main(String[] args) throws Exception {
        stringChange();
    }

    private static void createString() {
        String str1 = "hello";//字面量 只会在常量池中创建对象
        String str2 = str1.intern();
        System.out.println(str1==str2);//true

        String str3 = new String("world");//new 关键字只会在堆中创建对象
        String str4 = str3.intern();
        System.out.println(str3 == str4);//false

        String str5 = str1 + str2;//变量拼接的字符串，会在常量池中和堆中都创建对象
        String str6 = str5.intern();//这里由于池中已经有对象了，直接返回的是对象本身，也就是堆中的对象
        System.out.println(str5 == str6);//true

        String str7 = "hello1" + "world1";//常量拼接的字符串，只会在常量池中创建对象
        String str8 = str7.intern();
        System.out.println(str7 == str8);//true
    }

    /**
     * value 被 final 修饰，只能保证引用不被改变，但是 value 所指向的堆中的数组，才是真实的数据，只要能够操作堆中的数组，依旧能改变数据。
     * 而且 value 是基本类型构成，那么一定是可变的，即使被声明为 private，我们也可以通过反射来改变。
     *
     *  String 被改变了，但是在代码里，几乎不会使用反射的机制去操作 String 字符串，所以，我们会认为 String 类型是不可变的。
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void stringChange() throws NoSuchFieldException, IllegalAccessException {
        String str = "vae";
        //打印原字符串
        System.out.println(str);//vae
        //获取String类中的value字段
        Field fieldStr = String.class.getDeclaredField("value");
        //因为value是private声明的，这里修改其访问权限
        fieldStr.setAccessible(true);
        //获取str对象上的value属性的值
        byte[] value = (byte[]) fieldStr.get(str);
        //将第一个字符修改为 V(小写改大写)
        value[0] = 'V';
        //打印修改之后的字符串
        System.out.println(str);//Vae
    }
}
