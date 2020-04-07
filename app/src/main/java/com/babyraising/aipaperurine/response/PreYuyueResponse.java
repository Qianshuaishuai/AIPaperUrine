package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.PreYuyueBean;

public class PreYuyueResponse {
    private int result;
    private String msg;
    private PreYuyueBean data;

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

    public PreYuyueBean getData() {
        return data;
    }

    public void setData(PreYuyueBean data) {
        this.data = data;
    }
}
