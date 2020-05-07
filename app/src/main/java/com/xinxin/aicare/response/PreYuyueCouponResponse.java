package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.PreYuyueCouponBean;

public class PreYuyueCouponResponse {
    private int result;
    private String msg;
    private PreYuyueCouponBean data;

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

    public PreYuyueCouponBean getData() {
        return data;
    }

    public void setData(PreYuyueCouponBean data) {
        this.data = data;
    }
}
