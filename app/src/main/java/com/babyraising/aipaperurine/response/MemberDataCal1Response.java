package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MemberDataCal1Bean;
import com.babyraising.aipaperurine.bean.MemberInfoBean;

public class MemberDataCal1Response {
    private int result;
    private String msg;
    private MemberDataCal1Bean data;

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

    public MemberDataCal1Bean getData() {
        return data;
    }

    public void setData(MemberDataCal1Bean data) {
        this.data = data;
    }
}
