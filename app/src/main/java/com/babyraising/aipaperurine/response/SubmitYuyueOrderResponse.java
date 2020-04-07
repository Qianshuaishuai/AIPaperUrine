package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.PayOrderWechatBean;
import com.babyraising.aipaperurine.bean.SubmitYuyueOrderBean;

public class SubmitYuyueOrderResponse {
    private int result;
    private String msg;
    private SubmitYuyueOrderBean data;

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

    public SubmitYuyueOrderBean getData() {
        return data;
    }

    public void setData(SubmitYuyueOrderBean data) {
        this.data = data;
    }
}
