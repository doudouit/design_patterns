package com.callback.async;

import com.callback.OrderResult;
import com.callback.sync.Store;

import java.util.Random;

/*异步*/
public class NoSyncBuyer implements OrderResult {

    private Store store;//商店

    private String buyerName;//购物者名

    private String goodsName;//所购商品名

    public NoSyncBuyer(Store store, String buyerName, String goodsName) {
        this.store = store;
        this.buyerName = buyerName;
        this.goodsName = goodsName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /*调用从商店返回订购物品的信息*/
    public String orderGoods() {
        String goodsState = "--";
        MyRunnable mr = new MyRunnable();
        Thread t = new Thread(mr);
        t.start();
        System.out.println(goodsState);
        goodsState = mr.getResult();// 得到返回值
        myFeeling();// 用来测试异步是不是还是按顺序的执行
        return goodsState;
    }

    public void myFeeling() {
        String[] s = {"有点小激动", "很期待!", "希望是个好货!"};
        Random random = new Random();
        int temp = random.nextInt(3);
        System.out.println("我是" + this.getBuyerName() + ", 我现在的感觉: " + s[temp]);
    }

    /*被回调的方法, 我们自己不去调用, 这个方法给出的结果, 是其他接口或者程序给我们的, 我们自己无法产生*/
    @Override
    public String getOrderResult(String state) {
        return "在" + this.getStore().getName() + "商店订购的" + this.getGoodsName() + "玩具, 目前的预订状态是: " + state;
    }

    // 开启另一个线程, 但是没有返回值, 怎么回事
    // 调试的时候, 等待一会儿, 还是可以取到值, 但不是立即取到, 在print显示的时候, 却是null, 需要注意?
    private class MyRunnable implements Runnable {

        private String result;

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                result = store.returnOrderGoodsInfo(NoSyncBuyer.this);// 匿名函数的时候, 无法return 返回值
            } catch (InterruptedException e) {
                System.out.println("出大事了, 异步回调有问题了" + e);
            }
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}