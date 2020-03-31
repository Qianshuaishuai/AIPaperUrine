package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.EditImgBean;
import com.babyraising.aipaperurine.bean.EditUserBean;

public class EditUserResponse {
    private int result;
    private String msg;
    private EditUserBean data;

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

    public EditUserBean getData() {
        return data;
    }

    public void setData(EditUserBean data) {
        this.data = data;
    }
}
