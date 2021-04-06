package com.patterns;

import java.util.HashMap;
import java.util.Map;

public class SubClass extends Parent {
    // 静态变量  
    public static String s_StaticField = "子类--静态变量";  
    // 变量  
    public String s_Field = "子类--变量";  
    // 静态初始化块  
    static {  
        System.out.println(s_StaticField);  
        System.out.println("子类--静态初始化块");  
    }  
    // 初始化块  
    {  
        System.out.println(s_Field);  
        System.out.println("子类--初始化块");  
    }  
    // 构造器  
    public SubClass() {  
        // this(1);
        super(1);
        System.out.println("子类--无参构造器");
    }

    public SubClass(int i) {
        System.out.println("父类--有参构造器" + i);
    }


    // 程序入口  
    public static void main(String[] args) {  
        System.out.println("*************in main***************");  
        new SubClass();  
        System.out.println("*************second subClass***************");  
        new SubClass();

//        HashMap

        Map<String,String> map = new HashMap<>();
        System.out.println(map.size());
        map.put("1","1");
        System.out.println(map.size());
    }

}  