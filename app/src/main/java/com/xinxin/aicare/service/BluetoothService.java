package com.xinxin.aicare.service;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.R;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.util.DataUtil;
import com.xinxin.aicare.util.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 蓝牙相关控制
 */
public class BluetoothService extends Service {

    private BluetoothLeScanner scanner;
    private ScanCallback mScanCallback;
    private ScanSettings mScanSettings;
    private AlertDialog boothDialog;

    private BluetoothAdapter blueadapter;
    private String currentDeviceCode = "";

    private Timer timer;
    private TimerTask timerTask;

    private boolean isStart = false;

    /**
     * 初始化蓝牙
     */
    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(currentDeviceCode)) {
//                    uploadDeviceData("0", currentDeviceCode, "0", "0", "0", "0", "0", "0", "0");
//                    System.out.println("updateZeroData");
                }

                if (isStart == false) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        blueadapter = BluetoothAdapter.getDefaultAdapter();
                        if (blueadapter != null) {
                            scanner = blueadapter.getBluetoothLeScanner();
                            if (scanner != null) {
                                startScan();
                                isStart = true;
                            }

                        }
                    }

                }
            }
        };
        timer.schedule(timerTask, 0, 1000);

        initBoothDialog();
        initBooth();
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
        timer.cancel();
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

        if (!blueadapter.isEnabled()) {//如果没打开，则打开蓝牙
            blueadapter.enable();
            return;
        }

//        startScan();
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
                        if (D0.equals("0")) {
                            System.out.println("清0成功");
                        } else {
                            System.out.println("实时上传数据成功");
                        }
//                        System.out.println("后台上传数据成功");
                        break;
                    case 2:
                        System.out.println("上传数据接结果:" + response.getMsg());
                        break;
                    case 1:

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

    private void startScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scanner = blueadapter.getBluetoothLeScanner();
            mScanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build();
            mScanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
//                    System.out.println("onScanResult");
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
                        currentDeviceCode = DEVICE_ID;
//                        if (D0.equals("0")) {
////                            uploadDeviceData(D0, currentDeviceCode, X, Y, Z, "0", "0", "0", D6);
//                            uploadDeviceData("0", DEVICE_ID, "0", "0", "0", "0", "0", "0", "0");
//                        } else {
//
//                        }
                        uploadDeviceData(D0, DEVICE_ID, X, Y, Z, AD, D4, D5, D6);
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

    private void initBoothDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        boothDialog = builder.create();
        boothDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        // 初始化控件，注意这里是通过view.findViewById
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("确定使用蓝牙？");

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                boothDialog.cancel();
            }
        });

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                boothDialog.cancel();
                blueadapter.enable();
            }
        });

        boothDialog.setCancelable(false);
//        tipDialog.show();
    }

}
