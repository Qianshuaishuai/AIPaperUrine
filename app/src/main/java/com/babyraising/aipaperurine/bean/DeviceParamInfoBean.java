package com.babyraising.aipaperurine.bean;

public class DeviceParamInfoBean {
    private DeviceParamInfoDefaultBean DEFAULT;
    private DeviceParamInfoMyParamBean MYPARAM;

    public DeviceParamInfoDefaultBean getDEFAULT() {
        return DEFAULT;
    }

    public void setDEFAULT(DeviceParamInfoDefaultBean DEFAULT) {
        this.DEFAULT = DEFAULT;
    }

    public DeviceParamInfoMyParamBean getMYPARAM() {
        return MYPARAM;
    }

    public void setMYPARAM(DeviceParamInfoMyParamBean MYPARAM) {
        this.MYPARAM = MYPARAM;
    }
}
