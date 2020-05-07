package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.CouponMoreBean;

import java.util.List;

public class CouponResponse {
    private int result;
    private String msg;

    private List<CouponMoreBean> data;

    public List<CouponMoreBean> getData() {
        return data;
    }

    public void setData(List<CouponMoreBean> data) {
        this.data = data;
    }

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
}
