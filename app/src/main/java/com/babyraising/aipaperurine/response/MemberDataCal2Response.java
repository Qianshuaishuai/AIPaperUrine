package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MemberDataCal1Bean;
import com.babyraising.aipaperurine.bean.MemberDataCal2Bean;

public class MemberDataCal2Response {
    private int result;
    private String msg;
    private MemberDataCal2Bean data;

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

    public MemberDataCal2Bean getData() {
        return data;
    }

    public void setData(MemberDataCal2Bean data) {
        this.data = data;
    }
}
