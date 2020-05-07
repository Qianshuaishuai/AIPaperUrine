package com.xinxin.aicare.service;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.R;
import com.xinxin.aicare.util.DataUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 蓝牙相关控制
 */
public class BluetoothService extends Service {

    boolean isDeBug = false;

    public boolean isDeBug() {
        return isDeBug;
    }

    private static final long TIME_OUT = 5000;
    private static final String TAG = "BluetoothService";
    public BluetoothBinder mBinder = new BluetoothBinder();
    private BleManager bleManager;
    private Handler threadHandler = new Handler(Looper.getMainLooper());
    // 下面的所有UUID及指令请根据实际设备替换
//    private static final String UUID_SERVICE = "70D51000-2C7F-4E75-AE8A-D758951CE4E0";
//    private static final String UUID_WRITE = "70D51001-2C7F-4E75-AE8A-D758951CE4E0";
//    private static final String UUID_READ = "70D51002-2C7F-4E75-AE8A-D758951CE4E0";

    private Callback mCallback = null;
    private DisConnectedCallback mHomePageCallback = null;

    BleDevice scanResult;//当前链接设备;

    private Timer timer;
    private TimerTask timerTask;


    /**
     * 初始化蓝牙
     */
    @Override
    public void onCreate() {
        super.onCreate();
        bleManager = BleManager.getInstance();
        bleManager.enableBluetooth();//开启蓝牙

    }

    /**
     * 关闭蓝牙链接
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        closeConnect();
        bleManager = null;
    }

    /**
     * 绑定成功返回代理
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
//        LogUtils.printStartLaunch("onBind");
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                scanDevice();
            }
        };

        timer.schedule(timerTask, 0, 3000);
        return mBinder;
    }

    /**
     * 解绑成功 关闭链接
     *
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        closeConnect();
        timer.cancel();
        return super.onUnbind(intent);
    }

    public class BluetoothBinder extends Binder {
        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    /**
     * 设置回调接口
     *
     * @param callback
     */
    public void setScanCallback(Callback callback) {
        mCallback = callback;
    }

    /**
     * 切换线程
     *
     * @param runnable
     */
    private void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            threadHandler.post(runnable);
        }
    }

    /**
     * 扫描设备
     */
    public void scanDevice() {
        if (mCallback != null) {
            mCallback.onStartScan();
        }
        bleManager.scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(final BleDevice bleDevice) {
                Log.i("Mark", "扫描导数据");
                upLoadDeviceData(bleDevice);
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback != null) {
                            mCallback.onScanning(bleDevice);
                        }
                    }
                });
            }

            @Override
            public void onScanFinished(final List<BleDevice> scanResultList) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback != null) {
                            mCallback.onScanComplete(scanResultList);
                        }
                    }
                });
            }
        });
    }

    /**
     * 停止扫描蓝牙设备
     */
    public void cancelScan() {
        if (bleManager != null)
            bleManager.cancelScan();
    }

    /**
     * 结束蓝牙连接
     */
    public void closeConnect() {
        if (bleManager != null) {
            bleManager.cancelScan();
        }
    }

    public interface DisConnectedCallback {
        void onDisConnected();
    }

    /**
     * 回调
     */
    public interface Callback {

        void onStartScan();

        void onScanning(BleDevice scanResult);

        void onScanComplete(final List<BleDevice> results);

        void onConnecting();

        void onConnectSuccess();

        void onConnectFail();

        void onDisConnected();

        void onServicesDiscovered(BluetoothGatt gatt, BleDevice bleDevice);

    }

    /**
     * 上传设备信息
     *
     * @param bleDevice
     */
    private void upLoadDeviceData(BleDevice bleDevice) {
        if (Constant.DEVICE_NAME.equals(bleDevice.getName())) {//设备名称为LN-200C
            byte[] scanRecord = bleDevice.getScanRecord();
            Log.i("Mark", "扫描的数据为   " + DataUtil.bytesHexString(scanRecord));
            UpLoadDeviceDataRequest upLoadDeviceDataRequest = new UpLoadDeviceDataRequest();
            upLoadDeviceDataRequest.setDEVICE_ID(bleDevice.getMac().replace(":", "").toLowerCase());
            upLoadDeviceDataRequest.setX(String.valueOf(Constants.normalHexByteToInt(scanRecord[11])));
            upLoadDeviceDataRequest.setY(String.valueOf(Constants.normalHexByteToInt(scanRecord[12])));
            upLoadDeviceDataRequest.setZ(String.valueOf(Constants.normalHexByteToInt(scanRecord[13])));
            upLoadDeviceDataRequest.setD0(String.valueOf(Constants.normalHexByteToInt(scanRecord[14])));
            upLoadDeviceDataRequest.setAD(String.valueOf(Constants.concat(scanRecord[15], scanRecord[16])));
            Log.i("Mark", "扫描的数据为   转换" + Constants.concat(scanRecord[15], scanRecord[16]));


            upLoadDeviceDataRequest.setD4(String.valueOf(Constants.normalHexByteToInt(scanRecord[18])));
            upLoadDeviceDataRequest.setD5(String.valueOf(Constants.normalHexByteToInt(scanRecord[19])));
            upLoadDeviceDataRequest.setD6(String.valueOf(Constants.normalHexByteToInt(scanRecord[20])));
            UpLoadDeviceDataUseCase upLoadDeviceDataUseCase = new UpLoadDeviceDataUseCase();
            upLoadDeviceDataUseCase.execute(upLoadDeviceDataRequest).subscribe(new CommonConsumer<CommonResponse>(new CommonConsumer.OnApiErrorListener() {
                @Override
                public boolean onApiErrorEvent(ApiException e) {
                    return true;
                }
            }) {
                @Override
                protected void accept(CommonResponse commonResponse) {

                }
            });

        }
    }
}
