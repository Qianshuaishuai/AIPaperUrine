package com.xinxin.aicare.bean;

import java.util.List;

public class MemberDataCal2Bean {
    private String COMMENT;
    private String VIEW;
    private String RECOMMEND_VOLUME;
    private List<MemberDataCal2ListBean> DATALIST;

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

    public String getRECOMMEND_VOLUME() {
        return RECOMMEND_VOLUME;
    }

    public void setRECOMMEND_VOLUME(String RECOMMEND_VOLUME) {
        this.RECOMMEND_VOLUME = RECOMMEND_VOLUME;
    }

    public List<MemberDataCal2ListBean> getDATALIST() {
        return DATALIST;
    }

    public void setDATALIST(List<MemberDataCal2ListBean> DATALIST) {
        this.DATALIST = DATALIST;
    }
}
