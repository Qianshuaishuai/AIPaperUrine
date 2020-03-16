package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.CourseBean;

public class CourseResponse {
    private int result;
    private String msg;
    private CourseBean data;

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

    public CourseBean getData() {
        return data;
    }

    public void setData(CourseBean data) {
        this.data = data;
    }
}
