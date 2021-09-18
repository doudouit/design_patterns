package com.patterns.chainOfResponsibility.v3;

import lombok.Data;

//@Component
public class SkuInfoHandler extends AbstractDataHandler<SkuInfoHandler.SkuInfo> {
    @Override
    protected SkuInfoHandler.SkuInfo doRequest(String query) {
        SkuInfoHandler.SkuInfo info = new SkuInfoHandler.SkuInfo();
        info.setSkuId(78910L);
        info.setSkuName("测试SKU");
        return info;
    }
    @Data
    public static class SkuInfo {
        private Long skuId;
        private String skuName;
    }
}