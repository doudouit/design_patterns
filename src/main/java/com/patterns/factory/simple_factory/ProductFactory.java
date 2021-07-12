package com.patterns.factory.simple_factory;

/**
 * @decription:
 * @author: 180449
 * @date 2021/4/20 15:01
 */
public class ProductFactory extends FactoryMethod{

    @Override
    protected Product createProduct(String activityId) {
        if (EnumProductType.activityOne.getName().equals(activityId)) {
            return new OneProduct();
        } else if (EnumProductType.activityTwo.getName().equals(activityId)) {
            return new TwoProduct();
        }
        return null;
    }

    public static class OneProduct extends Product {}
    public static class TwoProduct extends Product {}

    public static void main(String[] args) {
        FactoryMethod factoryMethod = new ProductFactory();
        Product product = factoryMethod.product("one", "one");
        System.out.println(product.getProductName());
    }
}
