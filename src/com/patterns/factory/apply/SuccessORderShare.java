package com.patterns.factory.apply;

/**
 * @decription:
 * @author: 180449
 * @date 2021/4/20 15:42
 */
public class SuccessORderShare implements Share{
    @Override
    public String getShareFunctionType() {
        return ShareFactory.EnumShareType.SUCCESS_ORDER.getName();
    }

    @Override
    public String mainProcess(String shareName) {
        // 处理分享的业务流程
        return shareName;
    }

    public static void main(String[] args) {
        ShareFactory shareFactory = new ShareFactory();
        Share shareFunction = shareFactory.getShareFunction(ShareFactory.EnumShareType.SUCCESS_ORDER.getName());
        String success_order = shareFunction.mainProcess("Success order");
        System.out.println(success_order);
    }
}
