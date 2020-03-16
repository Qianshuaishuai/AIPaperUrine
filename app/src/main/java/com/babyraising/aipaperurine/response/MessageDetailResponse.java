package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.MessageDetailBean;
import com.babyraising.aipaperurine.bean.MessageReadBean;

import java.util.List;

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
