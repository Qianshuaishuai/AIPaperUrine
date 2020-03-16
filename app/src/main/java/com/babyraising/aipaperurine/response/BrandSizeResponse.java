package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.BrandSizeBean;

import java.util.List;

public class BrandSizeResponse {
    private int result;
    private String msg;
    private List<BrandSizeBean> data;

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

    public List<BrandSizeBean> getData() {
        return data;
    }

    public void setData(List<BrandSizeBean> data) {
        this.data = data;
    }
}
