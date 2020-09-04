package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.AboutUsBean;
import com.xinxin.aicare.bean.EditMemberBean;

public class EditMemberSizeResponse {
    private int result;
    private String msg;
    private EditMemberBean data;

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

    public EditMemberBean getData() {
        return data;
    }

    public void setData(EditMemberBean data) {
        this.data = data;
    }
}
