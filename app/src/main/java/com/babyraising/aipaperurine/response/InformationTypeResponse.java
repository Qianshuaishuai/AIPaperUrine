package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.InformationTypeBean;

import java.util.List;

public class InformationTypeResponse {
    private int result;
    private String msg;

    private List<InformationTypeBean> data;

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

    public List<InformationTypeBean> getData() {
        return data;
    }

    public void setData(List<InformationTypeBean> data) {
        this.data = data;
    }
}
