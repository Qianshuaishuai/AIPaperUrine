package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.ShowMyYuyueBean;
import com.babyraising.aipaperurine.bean.SubmitYuyueOrderBean;

public class ShowMyYuyueResponse {
    private int result;
    private String msg;
    private ShowMyYuyueBean data;

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

    public ShowMyYuyueBean getData() {
        return data;
    }

    public void setData(ShowMyYuyueBean data) {
        this.data = data;
    }
}
