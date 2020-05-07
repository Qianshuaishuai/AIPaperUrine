package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MemberListBean;

import java.util.List;

public class MemberListResponse {
    private int result;
    private String msg;
    private List<MemberListBean> data;

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

    public List<MemberListBean> getData() {
        return data;
    }

    public void setData(List<MemberListBean> data) {
        this.data = data;
    }
}
