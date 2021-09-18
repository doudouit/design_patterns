package com.patterns.chainOfResponsibility.v3;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

//@Component
public class DataAggregation {
    //@Autowired
    private SkuInfoHandler skuInfoHandler = new SkuInfoHandler();
    //@Autowired
    private ItemInfoHandler itemInfoHandler = new ItemInfoHandler();

    public Map convertItemDetail() throws Exception {
        Map result = new HashMap();
        result.put("skuInfoHandler", skuInfoHandler.doRequest("模拟数据请求"));
        result.put("itemInfoHandler", itemInfoHandler.doRequest("模拟数据请求"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //DataAggregation dataAggregation = (DataAggregation) applicationContext.getBean("dataAggregation");
        DataAggregation dataAggregation = new DataAggregation();
        Map map = dataAggregation.convertItemDetail();
        System.out.println(JSON.toJSONString(map));
        // 打印的结果数据
        // {"skuInfoHandler":{"skuId":78910,"skuName":"测试SKU"},"itemInfoHandler":{"itemId":123456,"itemName":"测试商品"}}
    }
}