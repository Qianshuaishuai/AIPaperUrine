package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.UserBean;

public class PersonResponse {
    private int result;
    private String msg;
    private PersonBean data;

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

    public PersonBean getData() {
        return data;
    }

    public void setData(PersonBean data) {
        this.data = data;
    }
}
