package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MemberDataCal3Bean;
import com.babyraising.aipaperurine.bean.MemberDataCal4Bean;

import java.util.List;

public class MemberDataCal4Response {
    private int result;
    private String msg;
    private List<MemberDataCal4Bean> data;

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

    public List<MemberDataCal4Bean> getData() {
        return data;
    }

    public void setData(List<MemberDataCal4Bean> data) {
        this.data = data;
    }
}
