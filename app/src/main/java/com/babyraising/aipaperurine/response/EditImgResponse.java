package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.EditImgBean;

public class EditImgResponse {
    private int result;
    private String msg;
    private EditImgBean data;

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

    public EditImgBean getData() {
        return data;
    }

    public void setData(EditImgBean data) {
        this.data = data;
    }
}
