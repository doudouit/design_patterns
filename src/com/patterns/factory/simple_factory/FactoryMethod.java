package com.patterns.factory.simple_factory;

/**
 * @decription: 工厂方法实现
 * @author: 180449
 * @date 2021/4/20 14:56
 */
public abstract class FactoryMethod {

    protected abstract Product createProduct(String name);

    public Product product(String activity, String name) {
        Product product = createProduct(activity);
        product.setProductName(name);
        return product;
    }

    public static class Product{
        private String productName;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }

    public enum EnumProductType {
        activityOne("one"),
        activityTwo("two");

        private String name;

        EnumProductType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
