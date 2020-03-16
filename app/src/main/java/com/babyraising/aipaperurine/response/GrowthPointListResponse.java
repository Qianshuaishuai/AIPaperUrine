package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.GrowthPointBean;

import java.util.List;

public class GrowthPointListResponse {
    private int result;
    private String msg;
    private List<GrowthPointBean> data;

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

    public List<GrowthPointBean> getData() {
        return data;
    }

    public void setData(List<GrowthPointBean> data) {
        this.data = data;
    }
}
