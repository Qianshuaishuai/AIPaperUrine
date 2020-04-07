package com.babyraising.aipaperurine.bean;

public class PreYuyueCouponBean {
    private String TOTAL_PRICE;
    private String TOTAL_NUM;
    private String EXPRESSFEE;
    private String SERVICE;
    private String COUPON_DISCOUNT;
    private String REAL_PAY;
    private PreYuyueUseCouponBean USE_COUPON;

    public String getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public void setTOTAL_PRICE(String TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public String getTOTAL_NUM() {
        return TOTAL_NUM;
    }

    public void setTOTAL_NUM(String TOTAL_NUM) {
        this.TOTAL_NUM = TOTAL_NUM;
    }

    public String getEXPRESSFEE() {
        return EXPRESSFEE;
    }

    public void setEXPRESSFEE(String EXPRESSFEE) {
        this.EXPRESSFEE = EXPRESSFEE;
    }

    public String getSERVICE() {
        return SERVICE;
    }

    public void setSERVICE(String SERVICE) {
        this.SERVICE = SERVICE;
    }

    public String getCOUPON_DISCOUNT() {
        return COUPON_DISCOUNT;
    }

    public void setCOUPON_DISCOUNT(String COUPON_DISCOUNT) {
        this.COUPON_DISCOUNT = COUPON_DISCOUNT;
    }

    public String getREAL_PAY() {
        return REAL_PAY;
    }

    public void setREAL_PAY(String REAL_PAY) {
        this.REAL_PAY = REAL_PAY;
    }

    public PreYuyueUseCouponBean getUSE_COUPON() {
        return USE_COUPON;
    }

    public void setUSE_COUPON(PreYuyueUseCouponBean USE_COUPON) {
        this.USE_COUPON = USE_COUPON;
    }
}
