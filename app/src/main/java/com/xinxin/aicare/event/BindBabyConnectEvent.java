package com.xinxin.aicare.event;

public class BindBabyConnectEvent {
    private String DEVICE_CODE;

    public String getDEVICE_CODE() {
        return DEVICE_CODE;
    }

    public void setDEVICE_CODE(String DEVICE_CODE) {
        this.DEVICE_CODE = DEVICE_CODE;
    }

    public BindBabyConnectEvent(String DEVICE_CODE) {
        this.DEVICE_CODE = DEVICE_CODE;
    }
}
