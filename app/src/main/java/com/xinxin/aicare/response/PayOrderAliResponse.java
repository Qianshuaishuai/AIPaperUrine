package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.PayOrderAliBean;

public class PayOrderAliResponse {
    private int result;
    private String msg;
    private PayOrderAliBean data;

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

    public PayOrderAliBean getData() {
        return data;
    }

    public void setData(PayOrderAliBean data) {
        this.data = data;
    }
}
