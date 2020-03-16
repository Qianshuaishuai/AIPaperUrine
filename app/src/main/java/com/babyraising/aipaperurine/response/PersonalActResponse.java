package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.PersonalActBean;

public class PersonalActResponse {
    private int result;
    private String msg;
    private PersonalActBean data;

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

    public PersonalActBean getData() {
        return data;
    }

    public void setData(PersonalActBean data) {
        this.data = data;
    }
}
