package com.xinxin.aicare.response;

import com.xinxin.aicare.bean.GrowthRankingAllBean;

public class GrowthRankingResponse {
    private int result;
    private String msg;
    private GrowthRankingAllBean data;

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

    public GrowthRankingAllBean getData() {
        return data;
    }

    public void setData(GrowthRankingAllBean data) {
        this.data = data;
    }
}
