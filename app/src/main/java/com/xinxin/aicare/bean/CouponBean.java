package com.xinxin.aicare.bean;

public class CouponBean {
    private String STARTDATE;
    private String ENDDATE;
    private String THRESHOLDAMT;
    private String TYPE;
    private String AMT;
    private String DISCOUNT;
    private String TIP;

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }

    public String getTHRESHOLDAMT() {
        return THRESHOLDAMT;
    }

    public void setTHRESHOLDAMT(String THRESHOLDAMT) {
        this.THRESHOLDAMT = THRESHOLDAMT;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getTIP() {
        return TIP;
    }

    public void setTIP(String TIP) {
        this.TIP = TIP;
    }
}
