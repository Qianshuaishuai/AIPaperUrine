package com.xinxin.aicare.event;

import com.xinxin.aicare.bean.BluetoothReceiveBean;

public class BluetoothReceiveEvent {
    private BluetoothReceiveBean bean;

    public BluetoothReceiveEvent(BluetoothReceiveBean bean){
        this.bean = bean;
    }

    public BluetoothReceiveBean getBean() {
        return bean;
    }

    public void setBean(BluetoothReceiveBean bean) {
        this.bean = bean;
    }
}
