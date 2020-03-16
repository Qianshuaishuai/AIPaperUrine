package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.UserGrowthRankingBean;

public class UserGrowthRankingResponse {
    private int result;
    private String msg;
    private UserGrowthRankingBean data;

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

    public UserGrowthRankingBean getData() {
        return data;
    }

    public void setData(UserGrowthRankingBean data) {
        this.data = data;
    }
}
