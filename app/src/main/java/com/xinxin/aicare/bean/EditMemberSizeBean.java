package com.xinxin.aicare.bean;

public class EditMemberSizeBean {
    private String THERMOMETER;
    private String NUMERICAL_TABLE;
    private String WATER_HOLDING_VALUE;
    private String ALARM_LIMIT_VALUE;

    public String getTHERMOMETER() {
        return THERMOMETER;
    }

    public void setTHERMOMETER(String THERMOMETER) {
        this.THERMOMETER = THERMOMETER;
    }

    public String getNUMERICAL_TABLE() {
        return NUMERICAL_TABLE;
    }

    public void setNUMERICAL_TABLE(String NUMERICAL_TABLE) {
        this.NUMERICAL_TABLE = NUMERICAL_TABLE;
    }

    public String getWATER_HOLDING_VALUE() {
        return WATER_HOLDING_VALUE;
    }

    public void setWATER_HOLDING_VALUE(String WATER_HOLDING_VALUE) {
        this.WATER_HOLDING_VALUE = WATER_HOLDING_VALUE;
    }

    public String getALARM_LIMIT_VALUE() {
        return ALARM_LIMIT_VALUE;
    }

    public void setALARM_LIMIT_VALUE(String ALARM_LIMIT_VALUE) {
        this.ALARM_LIMIT_VALUE = ALARM_LIMIT_VALUE;
    }
}
