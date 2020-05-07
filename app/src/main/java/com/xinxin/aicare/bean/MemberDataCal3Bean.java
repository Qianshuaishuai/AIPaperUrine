package com.xinxin.aicare.bean;

import java.util.List;

public class MemberDataCal3Bean {
    private String COMMENT;
    private String VIEW;
    private List<MemberDataCal3ListBean> DATALIST;

    public String getCOMMENT() {
        return COMMENT;
    }

    public void setCOMMENT(String COMMENT) {
        this.COMMENT = COMMENT;
    }

    public String getVIEW() {
        return VIEW;
    }

    public void setVIEW(String VIEW) {
        this.VIEW = VIEW;
    }

    public List<MemberDataCal3ListBean> getDATALIST() {
        return DATALIST;
    }

    public void setDATALIST(List<MemberDataCal3ListBean> DATALIST) {
        this.DATALIST = DATALIST;
    }
}
