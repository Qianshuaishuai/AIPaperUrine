package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.EditMemberImgBean;

public class EditMemberImgResponse {
    private int result;
    private String msg;
    private EditMemberImgBean data;

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

    public EditMemberImgBean getData() {
        return data;
    }

    public void setData(EditMemberImgBean data) {
        this.data = data;
    }
}
