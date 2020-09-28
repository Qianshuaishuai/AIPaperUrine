package com.xinxin.aicare.event;

import com.xinxin.aicare.bean.BluetoothReceiveBean;

public class BluetoothConnectEvent {
    private int status;

    public BluetoothConnectEvent(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
