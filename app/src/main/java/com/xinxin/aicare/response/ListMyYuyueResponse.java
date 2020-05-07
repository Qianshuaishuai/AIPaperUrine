package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.YuYueBean;

import java.util.List;

public class ListMyYuyueResponse {
    private int result;
    private String msg;
    private List<YuYueBean> data;

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

    public List<YuYueBean> getData() {
        return data;
    }

    public void setData(List<YuYueBean> data) {
        this.data = data;
    }
}
