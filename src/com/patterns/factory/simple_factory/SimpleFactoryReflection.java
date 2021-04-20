package com.patterns.factory.simple_factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 在高并发的时候避免反射实现
 * 这里还有一个问题适用反射不当是容易导致线上机器出问题的，因为我们反射创建的对象属性是被SoftReference软引用的，
 * 所以当**-XX:SoftRefLRUPolicyMSPerMB** 没有设置好的话会一直让机器CPU很高。
 * 当然他的默认值是1000，也就根据大家的情况而定吧，反正就是注意一下这点。
 *
 * @decription: 简单工厂反射实现
 * @author: 180449
 * @date 2021/4/20 14:33
 */
public class SimpleFactoryReflection {

    private static final Map<EnumProductType, Class> activityMap = new HashMap<>();

    public static void addProductKey(EnumProductType enumProduct, Class product) {
        activityMap.put(enumProduct, product);
    }

    public static activityOne product(EnumProductType type) throws IllegalAccessException, InstantiationException {
        Class productClass = activityMap.get(type);
        return (activityOne) productClass.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        addProductKey(EnumProductType.activityOne, activityOne.class);
        activityOne product = product(EnumProductType.activityOne);
        System.out.println(product.toString());
    }

    public static class Product {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class activityOne extends Product {
        private String stock;

        @Override
        public String toString() {
            return "activityOne{" +
                    "stock='" + stock + '\'' +
                    '}';
        }
    }


    public static class activityTwo extends Product {
        private String stock;

        @Override
        public String toString() {
            return "ActivityOne{" +
                    "stock='" + stock + '\'' +
                    '}';
        }
    }


    public enum EnumProductType {
        activityOne,
        activityTwo;
    }
}
