package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.AboutUsBean;
import com.babyraising.aipaperurine.bean.PreRefundMyYuyueBean;

public class PreRefundMyYuyueResponse {
    private int result;
    private String msg;
    private PreRefundMyYuyueBean data;

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

    public PreRefundMyYuyueBean getData() {
        return data;
    }

    public void setData(PreRefundMyYuyueBean data) {
        this.data = data;
    }
}
