package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.UseragreementBean;
import com.xinxin.aicare.bean.VersionBean;

public class UseragreementResponse {
    private int result;
    private String msg;
    private UseragreementBean data;

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

    public UseragreementBean getData() {
        return data;
    }

    public void setData(UseragreementBean data) {
        this.data = data;
    }
}
