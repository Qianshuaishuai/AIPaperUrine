package com.babyraising.aipaperurine.response;

import com.babyraising.aipaperurine.bean.DeviceParamInfoBean;
import com.babyraising.aipaperurine.bean.DeviceParamInfoDefaultBean;
import com.babyraising.aipaperurine.bean.DeviceParamInfoMyParamBean;

public class DeviceParamInfoResponse {
    private int result;
    private String msg;
    private DeviceParamInfoBean data;

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

    public DeviceParamInfoBean getData() {
        return data;
    }

    public void setData(DeviceParamInfoBean data) {
        this.data = data;
    }
}
