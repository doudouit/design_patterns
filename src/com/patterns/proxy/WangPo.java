package com.patterns.proxy;

/**
 * 王婆这人老聪明了，但是太老了，是个男人都看不上她
 * 有智慧经验，作为女代理
 */
public class WangPo implements KindWomen {

    private KindWomen kindWomen;

    public WangPo(){
        // 默认给潘金莲代理
        this.kindWomen = new PanJinLian();
    }

    public WangPo(KindWomen kindWomen) {
        // 可以是任何人的代理，只要你是这种类型的
        this.kindWomen = kindWomen;
    }

    @Override
    public void makeEyeWithMan() {
        this.kindWomen.makeEyeWithMan();
    }

    @Override
    public void happyMan() {
        this.kindWomen.happyMan();
    }
}
