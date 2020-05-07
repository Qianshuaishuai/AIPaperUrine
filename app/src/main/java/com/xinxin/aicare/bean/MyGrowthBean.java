package com.xinxin.aicare.bean;

import java.util.List;

public class MyGrowthBean {
    private String NICKNAME;
    private String HEADIMG;
    private String LEVEL;
    private String MyGrowthRanking;
    private String GROWTH;
    private String NEED_GROWTH;
    private String CONTINUITTY_SIGNIN_NUM;
    private String DIAPER_ALL;
    private List<GrowthPointBean> LIST;

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

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getMyGrowthRanking() {
        return MyGrowthRanking;
    }

    public void setMyGrowthRanking(String myGrowthRanking) {
        MyGrowthRanking = myGrowthRanking;
    }

    public String getGROWTH() {
        return GROWTH;
    }

    public void setGROWTH(String GROWTH) {
        this.GROWTH = GROWTH;
    }

    public String getNEED_GROWTH() {
        return NEED_GROWTH;
    }

    public void setNEED_GROWTH(String NEED_GROWTH) {
        this.NEED_GROWTH = NEED_GROWTH;
    }

    public String getCONTINUITTY_SIGNIN_NUM() {
        return CONTINUITTY_SIGNIN_NUM;
    }

    public void setCONTINUITTY_SIGNIN_NUM(String CONTINUITTY_SIGNIN_NUM) {
        this.CONTINUITTY_SIGNIN_NUM = CONTINUITTY_SIGNIN_NUM;
    }

    public String getDIAPER_ALL() {
        return DIAPER_ALL;
    }

    public void setDIAPER_ALL(String DIAPER_ALL) {
        this.DIAPER_ALL = DIAPER_ALL;
    }

    public List<GrowthPointBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<GrowthPointBean> LIST) {
        this.LIST = LIST;
    }
}
