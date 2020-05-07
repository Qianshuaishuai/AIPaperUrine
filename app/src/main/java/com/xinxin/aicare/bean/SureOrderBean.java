package com.xinxin.aicare.bean;

public class SureOrderBean {
    private String selectValue1;
    private String selectValue2;
    private String selectValue3;
    private String goodCount;
    private GoodsInfoBean goodsInfoBean;

    public String getSelectValue1() {
        return selectValue1;
    }

    public void setSelectValue1(String selectValue1) {
        this.selectValue1 = selectValue1;
    }

    public String getSelectValue2() {
        return selectValue2;
    }

    public void setSelectValue2(String selectValue2) {
        this.selectValue2 = selectValue2;
    }

    public String getSelectValue3() {
        return selectValue3;
    }

    public void setSelectValue3(String selectValue3) {
        this.selectValue3 = selectValue3;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public GoodsInfoBean getGoodsInfoBean() {
        return goodsInfoBean;
    }

    public void setGoodsInfoBean(GoodsInfoBean goodsInfoBean) {
        this.goodsInfoBean = goodsInfoBean;
    }
}
