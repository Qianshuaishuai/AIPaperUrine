package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.AboutUsBean;
import com.babyraising.aipaperurine.bean.MemberChangeInfoBean;

public class MemberChangeInfoResponse {
    private int result;
    private String msg;
    private MemberChangeInfoBean data;

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

    public MemberChangeInfoBean getData() {
        return data;
    }

    public void setData(MemberChangeInfoBean data) {
        this.data = data;
    }
}
