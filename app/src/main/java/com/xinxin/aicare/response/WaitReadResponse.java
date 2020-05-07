package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MessageReadBean;

public class WaitReadResponse {
    private int result;
    private String msg;

    private MessageReadBean data;

    public MessageReadBean getData() {
        return data;
    }

    public void setData(MessageReadBean data) {
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
