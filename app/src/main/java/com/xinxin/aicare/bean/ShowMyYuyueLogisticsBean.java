package com.xinxin.aicare.bean;

import java.util.List;

public class ShowMyYuyueLogisticsBean {
    private String TNO;
    private String TNAME;
    private String STATE;
    private List<TraceBean> Traces;

    public String getTNO() {
        return TNO;
    }

    public void setTNO(String TNO) {
        this.TNO = TNO;
    }

    public String getTNAME() {
        return TNAME;
    }

    public void setTNAME(String TNAME) {
        this.TNAME = TNAME;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public List<TraceBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TraceBean> traces) {
        Traces = traces;
    }
}
