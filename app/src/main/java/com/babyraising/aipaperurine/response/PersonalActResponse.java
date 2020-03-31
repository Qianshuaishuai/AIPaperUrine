package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.PersonalActBean;

import java.util.List;

public class PersonalActResponse {
    private int result;
    private String msg;
    private List<PersonalActBean> data;

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

    public List<PersonalActBean> getData() {
        return data;
    }

    public void setData(List<PersonalActBean> data) {
        this.data = data;
    }
}
