package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.AboutUsBean;
import com.babyraising.aipaperurine.bean.PreRefundNumBean;

public class PreRefundNumResponse {
    private int result;
    private String msg;
    private PreRefundNumBean data;

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

    public PreRefundNumBean getData() {
        return data;
    }

    public void setData(PreRefundNumBean data) {
        this.data = data;
    }
}
