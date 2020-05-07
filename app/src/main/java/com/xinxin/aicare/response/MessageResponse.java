package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MessageBean;

import java.util.List;

public class MessageResponse {
    private int result;
    private String msg;
    private List<MessageBean> data;

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

    public List<MessageBean> getData() {
        return data;
    }

    public void setData(List<MessageBean> data) {
        this.data = data;
    }
}
