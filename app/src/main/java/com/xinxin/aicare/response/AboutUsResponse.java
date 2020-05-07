package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.AboutUsBean;

public class AboutUsResponse {
    private int result;
    private String msg;
    private AboutUsBean data;

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

    public AboutUsBean getData() {
        return data;
    }

    public void setData(AboutUsBean data) {
        this.data = data;
    }
}
