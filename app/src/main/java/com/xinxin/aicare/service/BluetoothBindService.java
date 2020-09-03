package com.xinxin.aicare.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.event.BindSuccessEvent;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.util.DataUtil;
import com.xinxin.aicare.util.T;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 蓝牙相关控制
 */
public class BluetoothBindService extends Service {

    private BluetoothLeScanner scanner;
    private ScanCallback mScanCallback;
    private ScanSettings mScanSettings;

    private BluetoothAdapter blueadapter;

    private String userId = "";
    private String onlineId = "";
    private String memberId = "";

    /**
     * 初始化蓝牙
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initBooth();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userId = intent.getStringExtra("userId");
        onlineId = intent.getStringExtra("onlineId");
        memberId = intent.getStringExtra("memberId");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 关闭蓝牙链接
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scanner.stopScan(mScanCallback);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initBooth() {
        blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (blueadapter == null) {
            //表示手机不支持蓝牙
            T.s("该手机不支持蓝牙");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scanner = blueadapter.getBluetoothLeScanner();
            mScanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build();
            mScanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    System.out.println("bindResult");
                    if (result.getDevice().getName() != null && result.getDevice().getName().contains(Constant.DEVICE_NAME)) {
                        byte[] datas = result.getScanRecord().getBytes();
                        String DEVICE_ID = result.getDevice().getAddress().replace(":", "").toLowerCase();
                        String X = String.valueOf(DataUtil.normalHexByteToInt(datas[11]));
                        String Y = String.valueOf(DataUtil.normalHexByteToInt(datas[12]));
                        String Z = String.valueOf(DataUtil.normalHexByteToInt(datas[13]));
                        String D0 = String.valueOf(DataUtil.normalHexByteToInt(datas[14]));
                        String AD = String.valueOf(DataUtil.concat(datas[15], datas[16]));
                        String D4 = String.valueOf(DataUtil.normalHexByteToInt(datas[18]));
                        String D5 = String.valueOf(DataUtil.normalHexByteToInt(datas[19]));
                        String D6 = String.valueOf(DataUtil.normalHexByteToInt(datas[20]));
                        System.out.println(userId);

                        if(!TextUtils.isEmpty(userId)){
                            System.out.println("bindDevice");
                            bindDevice(D0, DEVICE_ID, X, Y, Z, AD, D4, D5, D6);
                        }
                    }
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                }
            };
            scanner.startScan(null, mScanSettings, mScanCallback);
        }
    }

    private void bindDevice(final String D0, final String DEVICE_ID, final String X, final String Y, final String Z, final String AD, final String D4, final String D5, final String D6) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_BINDDEVICE);
        params.addQueryStringParameter("APPUSER_ID", userId);
        params.addQueryStringParameter("ONLINE_ID", onlineId);
        params.addQueryStringParameter("MEMBER_ID", memberId);
        params.addQueryStringParameter("DEVICE_CODE", DEVICE_ID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("绑定设备成功");
                        EventBus.getDefault().post(new BindSuccessEvent());
                        break;

                    default:
                        T.s(response.getMsg());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误处理:" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void uploadDeviceData(final String D0, final String DEVICE_ID, final String X, final String Y, final String Z, final String AD, final String D4, final String D5, final String D6) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_UPLOADDEVICEDATA);
        params.addQueryStringParameter("DEVICE_ID", DEVICE_ID);
        params.addQueryStringParameter("D0", D0);
        params.addQueryStringParameter("X", X);
        params.addQueryStringParameter("Y", Y);
        params.addQueryStringParameter("Z", Z);
        params.addQueryStringParameter("AD", AD);
        params.addQueryStringParameter("D4", D4);
        params.addQueryStringParameter("D5", D5);
        params.addQueryStringParameter("D6", D6);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
//                        System.out.println("后台上传数据成功");
                        break;
                    case 2:
                        System.out.println(response.getMsg());
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误处理:" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
