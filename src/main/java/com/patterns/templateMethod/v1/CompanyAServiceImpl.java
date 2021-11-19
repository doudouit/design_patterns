package com.patterns.templateMethod.v1;

/**
 * @decription: A公司
 * @author: 180449
 * @date 2021/11/19 15:50
 */
public class CompanyAServiceImpl extends AbstractMerchantService{
    @Override
    void queryMerchantInfo() {

    }

    @Override
    void signature() {

    }

    @Override
    void httpRequest() {

    }

    @Override
    void verifySinature() {

    }


    // 不代理
    @Override
    boolean isRequestByProxy() {
        return false;
    }
}
