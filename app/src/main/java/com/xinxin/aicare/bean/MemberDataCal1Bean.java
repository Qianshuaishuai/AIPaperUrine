package com.xinxin.aicare.bean;

import java.util.List;

public class MemberDataCal1Bean {
    private String DIAPER_CNT;
    private String AVG_DIAPER_CNT;
    private String COMMENT;
    private String VIEW;
    private String RECOMMEND_CNT;
    private List<MemberDataCal1ListBean> DATALIST;

    public String getDIAPER_CNT() {
        return DIAPER_CNT;
    }

    public void setDIAPER_CNT(String DIAPER_CNT) {
        this.DIAPER_CNT = DIAPER_CNT;
    }

    public String getAVG_DIAPER_CNT() {
        return AVG_DIAPER_CNT;
    }

    public void setAVG_DIAPER_CNT(String AVG_DIAPER_CNT) {
        this.AVG_DIAPER_CNT = AVG_DIAPER_CNT;
    }

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

    public String getRECOMMEND_CNT() {
        return RECOMMEND_CNT;
    }

    public void setRECOMMEND_CNT(String RECOMMEND_CNT) {
        this.RECOMMEND_CNT = RECOMMEND_CNT;
    }

    public List<MemberDataCal1ListBean> getDATALIST() {
        return DATALIST;
    }

    public void setDATALIST(List<MemberDataCal1ListBean> DATALIST) {
        this.DATALIST = DATALIST;
    }
}
