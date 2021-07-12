package com.callback.sync;

import com.callback.async.NoSyncBuyer;

public class Test2Callback {
    public static void main(String[] args) {
        Store wallMart = new Store("沙中路沃尔玛");
        SyncBuyer syncBuyer = new SyncBuyer(wallMart, "小明", "超能铁扇公主");
        System.out.println(syncBuyer.orderGoods());

        System.out.println("\n");
        Store lawson = new Store("沙中路罗森便利店");
        NoSyncBuyer noSyncBuyer = new NoSyncBuyer(lawson, "cherry", "变形金刚");
        System.out.println(noSyncBuyer.orderGoods());
    }
}