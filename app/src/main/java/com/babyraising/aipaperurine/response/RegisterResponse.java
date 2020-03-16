package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.UserBean;

public class RegisterResponse {
    private int result;
    private String msg;
    private UserBean data;

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

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }
}
