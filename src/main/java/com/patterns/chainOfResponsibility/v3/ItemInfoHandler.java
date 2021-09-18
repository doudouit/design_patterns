package com.patterns.chainOfResponsibility.v3;

import lombok.Data;

//@Component
public class ItemInfoHandler extends AbstractDataHandler<ItemInfoHandler.ItemInfo> {
    @Override
    protected ItemInfoHandler.ItemInfo doRequest(String query) {
        ItemInfoHandler.ItemInfo info = new ItemInfo();
        info.setItemId(123456L);
        info.setItemName("测试商品");
        return info;
    }

    @Data
    public static class ItemInfo {
        private Long itemId;
        private String itemName;
    }
}