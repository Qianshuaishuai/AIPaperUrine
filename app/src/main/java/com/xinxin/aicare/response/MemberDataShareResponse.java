package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MemberDataShareBean;

public class MemberDataShareResponse {
    private int result;
    private String msg;
    private MemberDataShareBean data;

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

    public MemberDataShareBean getData() {
        return data;
    }

    public void setData(MemberDataShareBean data) {
        this.data = data;
    }
}
