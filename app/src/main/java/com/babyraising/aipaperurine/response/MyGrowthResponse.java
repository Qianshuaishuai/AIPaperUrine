package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MyGrowthBean;

public class MyGrowthResponse {
    private int result;
    private String msg;
    private MyGrowthBean data;

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

    public MyGrowthBean getData() {
        return data;
    }

    public void setData(MyGrowthBean data) {
        this.data = data;
    }
}
