package com.xinxin.aicare.event;

public class ReplaceEvent {
    private String name;

    private String APPUSER_ID;
    private String ONLINE_ID;
    private String MEMBER_ID;
    private String DEVICE_CODE;

    public ReplaceEvent(String name, String APPUSER_ID, String ONLINE_ID, String MEMBER_ID, String DEVICE_CODE) {
        this.name = name;
        this.APPUSER_ID = APPUSER_ID;
        this.ONLINE_ID = ONLINE_ID;
        this.MEMBER_ID = MEMBER_ID;
        this.DEVICE_CODE = DEVICE_CODE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAPPUSER_ID() {
        return APPUSER_ID;
    }

    public void setAPPUSER_ID(String APPUSER_ID) {
        this.APPUSER_ID = APPUSER_ID;
    }

    public String getONLINE_ID() {
        return ONLINE_ID;
    }

    public void setONLINE_ID(String ONLINE_ID) {
        this.ONLINE_ID = ONLINE_ID;
    }

    public String getMEMBER_ID() {
        return MEMBER_ID;
    }

    public void setMEMBER_ID(String MEMBER_ID) {
        this.MEMBER_ID = MEMBER_ID;
    }

    public String getDEVICE_CODE() {
        return DEVICE_CODE;
    }

    public void setDEVICE_CODE(String DEVICE_CODE) {
        this.DEVICE_CODE = DEVICE_CODE;
    }
}
