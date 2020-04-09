package com.babyraising.aipaperurine.bean;

import java.util.List;

public class ShowMyYuyueBean {
    private String YUYUE_ID;
    private String PAYNO;
    private String CREATETIME;
    private String PAYSTATE;
    private String REALPAY;
    private String EXPRESSFEE;
    private String PAYMETHOD;
    private String PAYTIME;
    private String CNAME;
    private String CPHONE;
    private String ADDRESS;
    private String TOTAL_PRICE;
    private String COUPON_DIS_COUNT;
    private String GROWTH;
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

    public String getPAYMETHOD() {
        return PAYMETHOD;
    }

    public void setPAYMETHOD(String PAYMETHOD) {
        this.PAYMETHOD = PAYMETHOD;
    }

    public String getPAYTIME() {
        return PAYTIME;
    }

    public void setPAYTIME(String PAYTIME) {
        this.PAYTIME = PAYTIME;
    }

    public String getCNAME() {
        return CNAME;
    }

    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
    }

    public String getCPHONE() {
        return CPHONE;
    }

    public void setCPHONE(String CPHONE) {
        this.CPHONE = CPHONE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public void setTOTAL_PRICE(String TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public String getCOUPON_DIS_COUNT() {
        return COUPON_DIS_COUNT;
    }

    public void setCOUPON_DIS_COUNT(String COUPON_DIS_COUNT) {
        this.COUPON_DIS_COUNT = COUPON_DIS_COUNT;
    }

    public String getGROWTH() {
        return GROWTH;
    }

    public void setGROWTH(String GROWTH) {
        this.GROWTH = GROWTH;
    }

    public List<GoodBean> getGOODS() {
        return GOODS;
    }

    public void setGOODS(List<GoodBean> GOODS) {
        this.GOODS = GOODS;
    }
}
