package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.ShowMyYuyueLogisticsBean;

public class ShowMyYuyueLogisticsResponse {
    private int result;
    private String msg;
    private ShowMyYuyueLogisticsBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShowMyYuyueLogisticsBean getData() {
        return data;
    }

    public void setData(ShowMyYuyueLogisticsBean data) {
        this.data = data;
    }
}
