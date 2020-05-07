package com.xinxin.aicare.bean;

import java.util.List;

public class GrowthRankingAllBean {
    private List<GrowthRankingBean> GrowthRanking;
    private String MyGrowthRanking;
    private List<WeekGrowthRankingBean> WeekGrowthRanking;
    private String MyWeekGrowthRanking;

    public List<GrowthRankingBean> getGrowthRanking() {
        return GrowthRanking;
    }

    public void setGrowthRanking(List<GrowthRankingBean> growthRanking) {
        GrowthRanking = growthRanking;
    }

    public String getMyGrowthRanking() {
        return MyGrowthRanking;
    }

    public void setMyGrowthRanking(String myGrowthRanking) {
        MyGrowthRanking = myGrowthRanking;
    }

    public List<WeekGrowthRankingBean> getWeekGrowthRanking() {
        return WeekGrowthRanking;
    }

    public void setWeekGrowthRanking(List<WeekGrowthRankingBean> weekGrowthRanking) {
        WeekGrowthRanking = weekGrowthRanking;
    }

    public String getMyWeekGrowthRanking() {
        return MyWeekGrowthRanking;
    }

    public void setMyWeekGrowthRanking(String myWeekGrowthRanking) {
        MyWeekGrowthRanking = myWeekGrowthRanking;
    }
}
