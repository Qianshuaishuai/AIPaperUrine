package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MemberInfoBean;
import com.babyraising.aipaperurine.bean.MemberListBean;

import java.util.List;

public class MemberInfoResponse {
    private int result;
    private String msg;
    private MemberInfoBean data;

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

    public MemberInfoBean getData() {
        return data;
    }

    public void setData(MemberInfoBean data) {
        this.data = data;
    }
}
