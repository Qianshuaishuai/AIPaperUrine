package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.InformationListBean;

import java.util.List;

public class InformationListResponse {
    private int result;
    private String msg;

    private List<InformationListBean> data;

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

    public List<InformationListBean> getData() {
        return data;
    }

    public void setData(List<InformationListBean> data) {
        this.data = data;
    }
}
