package com.callback.sync;

import com.callback.OrderResult;

import java.util.Random;

public class Store {
    private String name;

    Store(String name) {
        this.name = name;
    }

    /*回调函数, 将结构传给那个我们不能直接调用的方法, 然后获取结果*/
    public String returnOrderGoodsInfo(OrderResult order) {
        String[] s = {"订购中...", "订购失败", "即将发货!", "运输途中...", "已在投递"};
        Random random = new Random();
        int temp = random.nextInt(5);
        String s1 = s[temp];
        return order.getOrderResult(s1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}