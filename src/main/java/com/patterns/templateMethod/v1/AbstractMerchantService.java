package com.patterns.templateMethod.v1;

/**
 * 抽象类定义骨架流程（查询商户信息，加签，http请求，验签）
 */
public abstract class AbstractMerchantService {

    //查询商户信息
    abstract void queryMerchantInfo();

    //加签
    abstract void signature();

    //http 请求
    abstract void httpRequest();

    // 验签
    abstract void verifySinature();

    //模板方法流程
    void handlerTempPlate(){
        //查询商户信息
        queryMerchantInfo();
        //加签
        signature();
        //http 请求
        httpRequest();
        // 验签
        verifySinature();
    }

    // Http是否走代理（提供给子类实现）
    abstract boolean isRequestByProxy();

}
