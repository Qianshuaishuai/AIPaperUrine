package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.AddressInfoBean;

public class AddressInfoResponse {
    private int result;
    private String msg;
    private AddressInfoBean data;

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

    public AddressInfoBean getData() {
        return data;
    }

    public void setData(AddressInfoBean data) {
        this.data = data;
    }
}
