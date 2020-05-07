package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.HomeCarouselBean;

import java.util.List;

public class HomeCarouselResponse {
    private int result;
    private String msg;
    private List<HomeCarouselBean> data;

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

    public List<HomeCarouselBean> getData() {
        return data;
    }

    public void setData(List<HomeCarouselBean> data) {
        this.data = data;
    }
}
