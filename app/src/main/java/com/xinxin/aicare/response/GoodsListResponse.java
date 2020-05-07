package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.GoodsForStoreBean;

import java.util.List;

public class GoodsListResponse {
    private int result;
    private String msg;
    private List<GoodsForStoreBean> data;

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

    public List<GoodsForStoreBean> getData() {
        return data;
    }

    public void setData(List<GoodsForStoreBean> data) {
        this.data = data;
    }
}
