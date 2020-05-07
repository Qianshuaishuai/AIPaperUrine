package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.ShowSimpleMyYuyueBean;

public class ShowSimpleMyYuyueResponse {
    private int result;
    private String msg;
    private ShowSimpleMyYuyueBean data;

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

    public ShowSimpleMyYuyueBean getData() {
        return data;
    }

    public void setData(ShowSimpleMyYuyueBean data) {
        this.data = data;
    }
}
