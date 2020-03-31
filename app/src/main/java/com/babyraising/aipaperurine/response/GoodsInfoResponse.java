package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.GoodsInfoBean;

public class GoodsInfoResponse {
    private int result;
    private String msg;
    private GoodsInfoBean data;

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

    public GoodsInfoBean getData() {
        return data;
    }

    public void setData(GoodsInfoBean data) {
        this.data = data;
    }
}
