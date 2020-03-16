package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.SignBean;
import com.babyraising.aipaperurine.bean.SignInfoBean;

public class SignResponse {
    private int result;
    private String msg;
    private SignBean data;

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

    public SignBean getData() {
        return data;
    }

    public void setData(SignBean data) {
        this.data = data;
    }
}
