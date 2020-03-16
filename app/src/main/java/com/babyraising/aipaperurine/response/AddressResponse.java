package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.AddressBean;

import java.util.List;

public class AddressResponse {
    private int result;
    private String msg;
    private List<AddressBean> data;

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

    public List<AddressBean> getData() {
        return data;
    }

    public void setData(List<AddressBean> data) {
        this.data = data;
    }
}
