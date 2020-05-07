package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.VersionBean;

public class VersionResponse {
    private int result;
    private String msg;
    private VersionBean data;

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

    public VersionBean getData() {
        return data;
    }

    public void setData(VersionBean data) {
        this.data = data;
    }
}
