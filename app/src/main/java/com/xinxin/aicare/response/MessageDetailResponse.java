package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MessageDetailBean;

public class MessageDetailResponse {
    private int result;
    private String msg;
    private MessageDetailBean data;

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

    public MessageDetailBean getData() {
        return data;
    }

    public void setData(MessageDetailBean data) {
        this.data = data;
    }
}
