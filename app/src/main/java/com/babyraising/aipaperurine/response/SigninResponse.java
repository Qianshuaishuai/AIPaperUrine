package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.SignInBean;

public class SigninResponse {
    private int result;
    private String msg;
    private SignInBean data;

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

    public SignInBean getData() {
        return data;
    }

    public void setData(SignInBean data) {
        this.data = data;
    }
}
