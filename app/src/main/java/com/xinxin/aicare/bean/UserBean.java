package com.xinxin.aicare.bean;

import java.util.List;

public class UserBean {
    private String APPUSER_ID;
    private String ONLINE_ID;
    private String PHONE;
    private String NICKNAME;
    private String HEADIMG;
    private String SEX;
    private String BIRTHDAY;
    private String CITY;
    private String PROVICE_CODE;
    private String CITY_CODE;
    private String DISTRICT_CODE;
    private String SIGNNAME;
    private String EMAIL;
    private String IS_PSW;
    private String GROWTH_ADD;
    private String POINT_ADD;
    private List<CouponBean> COUPON;

    public String getAPPUSER_ID() {
        return APPUSER_ID;
    }

    public void setAPPUSER_ID(String APPUSER_ID) {
        this.APPUSER_ID = APPUSER_ID;
    }

    public String getONLINE_ID() {
        return ONLINE_ID;
    }

    public void setONLINE_ID(String ONLINE_ID) {
        this.ONLINE_ID = ONLINE_ID;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }

    public String getHEADIMG() {
        return HEADIMG;
    }

    public void setHEADIMG(String HEADIMG) {
        this.HEADIMG = HEADIMG;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getPROVICE_CODE() {
        return PROVICE_CODE;
    }

    public void setPROVICE_CODE(String PROVICE_CODE) {
        this.PROVICE_CODE = PROVICE_CODE;
    }

    public String getCITY_CODE() {
        return CITY_CODE;
    }

    public void setCITY_CODE(String CITY_CODE) {
        this.CITY_CODE = CITY_CODE;
    }

    public String getDISTRICT_CODE() {
        return DISTRICT_CODE;
    }

    public void setDISTRICT_CODE(String DISTRICT_CODE) {
        this.DISTRICT_CODE = DISTRICT_CODE;
    }

    public String getSIGNNAME() {
        return SIGNNAME;
    }

    public void setSIGNNAME(String SIGNNAME) {
        this.SIGNNAME = SIGNNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getIS_PSW() {
        return IS_PSW;
    }

    public void setIS_PSW(String IS_PSW) {
        this.IS_PSW = IS_PSW;
    }

    public String getGROWTH_ADD() {
        return GROWTH_ADD;
    }

    public void setGROWTH_ADD(String GROWTH_ADD) {
        this.GROWTH_ADD = GROWTH_ADD;
    }

    public String getPOINT_ADD() {
        return POINT_ADD;
    }

    public void setPOINT_ADD(String POINT_ADD) {
        this.POINT_ADD = POINT_ADD;
    }

    public List<CouponBean> getCOUPON() {
        return COUPON;
    }

    public void setCOUPON(List<CouponBean> COUPON) {
        this.COUPON = COUPON;
    }
}
