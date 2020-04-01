package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MemberDataCal2Bean;
import com.babyraising.aipaperurine.bean.MemberDataCal3Bean;

public class MemberDataCal3Response {
    private int result;
    private String msg;
    private MemberDataCal3Bean data;

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

    public MemberDataCal3Bean getData() {
        return data;
    }

    public void setData(MemberDataCal3Bean data) {
        this.data = data;
    }
}
