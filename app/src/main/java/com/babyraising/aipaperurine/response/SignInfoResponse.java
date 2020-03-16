package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.SignInfoBean;

public class SignInfoResponse {
    private int result;
    private String msg;
    private SignInfoBean data;

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

    public SignInfoBean getData() {
        return data;
    }

    public void setData(SignInfoBean data) {
        this.data = data;
    }
}
