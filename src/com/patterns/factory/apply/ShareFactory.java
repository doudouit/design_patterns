package com.patterns.factory.apply;

import java.util.List;

/**
 * @decription: 分享模板
 * @author: 180449
 * @date 2021/4/20 15:20
 */
public class ShareFactory {

    private List<Share> shareFunctionList;


    public Share getShareFunction(String type) {
        for (Share share : shareFunctionList) {
            if (share.getShareFunctionType().equals(type)) {
                return share;
            }
        }
        return null;
    }

    public enum EnumShareType {
        SUCCESS_ORDER("successOrder");
        private String name;

        EnumShareType(String name) {
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
