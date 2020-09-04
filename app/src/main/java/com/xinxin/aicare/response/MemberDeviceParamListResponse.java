package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.MemberDataCal4Bean;
import com.xinxin.aicare.bean.MemberDeviceParamListBean;

import java.util.List;

public class MemberDeviceParamListResponse {
    private int result;
    private String msg;
    private List<MemberDeviceParamListBean> data;

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

    public List<MemberDeviceParamListBean> getData() {
        return data;
    }

    public void setData(List<MemberDeviceParamListBean> data) {
        this.data = data;
    }
}
