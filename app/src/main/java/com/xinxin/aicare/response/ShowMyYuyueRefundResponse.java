package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.ShowMyYuyueRefundBean;

public class ShowMyYuyueRefundResponse {
    private int result;
    private String msg;
    private ShowMyYuyueRefundBean data;

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

    public ShowMyYuyueRefundBean getData() {
        return data;
    }

    public void setData(ShowMyYuyueRefundBean data) {
        this.data = data;
    }
}
