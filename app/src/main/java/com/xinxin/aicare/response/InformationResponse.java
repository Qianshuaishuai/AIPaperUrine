package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.InformationBean;

public class InformationResponse {
    private int result;
    private String msg;
    private InformationBean data;

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

    public InformationBean getData() {
        return data;
    }

    public void setData(InformationBean data) {
        this.data = data;
    }
}
