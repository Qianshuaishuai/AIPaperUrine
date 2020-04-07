package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.PayOrderWechatBean;

public class PayOrderWechatResponse {
    private int result;
    private String msg;
    private PayOrderWechatBean data;

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

    public PayOrderWechatBean getData() {
        return data;
    }

    public void setData(PayOrderWechatBean data) {
        this.data = data;
    }
}
