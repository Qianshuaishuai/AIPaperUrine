package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MessageInfoBean;

public class MessageInfoResponse {
    private int result;
    private String msg;
    private MessageInfoBean data;

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

    public MessageInfoBean getData() {
        return data;
    }

    public void setData(MessageInfoBean data) {
        this.data = data;
    }
}
