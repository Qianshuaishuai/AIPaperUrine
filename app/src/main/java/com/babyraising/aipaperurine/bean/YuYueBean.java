package com.babyraising.aipaperurine.bean;

import java.util.List;

public class YuYueBean {
    private String YUYUE_ID;
    private String PAYNO;
    private String CREATETIME;
    private String PAYSTATE;
    private String REALPAY;
    private String EXPRESSFEE;
    private List<GoodBean> GOODS;

    public String getYUYUE_ID() {
        return YUYUE_ID;
    }

    public void setYUYUE_ID(String YUYUE_ID) {
        this.YUYUE_ID = YUYUE_ID;
    }

    public String getPAYNO() {
        return PAYNO;
    }

    public void setPAYNO(String PAYNO) {
        this.PAYNO = PAYNO;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getPAYSTATE() {
        return PAYSTATE;
    }

    public void setPAYSTATE(String PAYSTATE) {
        this.PAYSTATE = PAYSTATE;
    }

    public String getREALPAY() {
        return REALPAY;
    }

    public void setREALPAY(String REALPAY) {
        this.REALPAY = REALPAY;
    }

    public String getEXPRESSFEE() {
        return EXPRESSFEE;
    }

    public void setEXPRESSFEE(String EXPRESSFEE) {
        this.EXPRESSFEE = EXPRESSFEE;
    }

    public List<GoodBean> getGOODS() {
        return GOODS;
    }

    public void setGOODS(List<GoodBean> GOODS) {
        this.GOODS = GOODS;
    }
}
