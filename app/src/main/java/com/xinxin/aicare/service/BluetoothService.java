package com.xinxin.aicare.service;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.BluetoothReceiveBean;
import com.xinxin.aicare.bean.DeviceParamInfoMyParamBean;
import com.xinxin.aicare.event.BluetoothConnectEvent;
import com.xinxin.aicare.event.BluetoothReceiveEvent;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.util.DataUtil;
import com.xinxin.aicare.util.T;
import com.xinxin.aicare.util.UrineUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

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

    private String TAG = "BluetoothService";
    private BluetoothAdapter.LeScanCallback mBLEScanCallback;
    private boolean isConnect = false;
    private boolean isDataConnect = false;

    private String SERVICES_UUID = "0000fff0-0000-1000-8000-00805f9b34fb";   //服务UUID
    private String NOTIFY_UUID = "0000fff1-0000-1000-8000-00805f9b34fb";      //写入特征UUID
    private String DEVICE_ID = "";

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

//                if (!Constant.isDeviceBind) {
//                    return;
//                }

                if (isStart == false) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        blueadapter = BluetoothAdapter.getDefaultAdapter();
                        if (blueadapter != null) {
                            scanner = blueadapter.getBluetoothLeScanner();
                            if (scanner != null) {
                                System.out.println("scanning");
                                startScan();
//                                testScan();
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

//        connectBlueTooth();
    }

    private void connectBlueTooth() {
//        if (blueadapter == null) {
//            // 设备不支持蓝牙功能
//            System.out.println("设备不支持蓝牙功能");
//            return;
//        }
//        blueadapter.isDiscovering(); //监测蓝牙是否正在扫描
//        blueadapter.startDiscovery();//开始扫描
//
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mReceiver, filter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mBLEScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

                    BluetoothGatt bluetoothGatt;
                    System.out.println("nameaaa:" + device.getName());
                    if (device.getName() != null && device.getName().contains(Constant.DEVICE_NAME)) {
                        System.out.println("ssssssss");
                        BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
                            @Override
                            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                            }

                            @Override
                            public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                                super.onPhyRead(gatt, txPhy, rxPhy, status);
                            }

                            @Override
                            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                                super.onConnectionStateChange(gatt, status, newState);
                            }

                            @Override
                            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                                super.onServicesDiscovered(gatt, status);
                            }

                            @Override
                            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                                super.onCharacteristicRead(gatt, characteristic, status);
                            }

                            @Override
                            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                                super.onCharacteristicWrite(gatt, characteristic, status);
                            }

                            @Override
                            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                                super.onCharacteristicChanged(gatt, characteristic);
                            }

                            @Override
                            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                                super.onDescriptorRead(gatt, descriptor, status);
                            }

                            @Override
                            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                                super.onDescriptorWrite(gatt, descriptor, status);
                            }

                            @Override
                            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                                super.onReliableWriteCompleted(gatt, status);
                            }

                            @Override
                            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                                super.onReadRemoteRssi(gatt, rssi, status);
                            }

                            @Override
                            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                                super.onMtuChanged(gatt, mtu, status);
                            }
                        };

                        bluetoothGatt = device.connectGatt(getApplicationContext(), true, mGattCallback);
                    }

                }
            };

            blueadapter.startLeScan(mBLEScanCallback);
        }

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

        startScan();
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
//                System.out.println("错误处理:" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void testScan() {
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备绑定状态改变
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //蓝牙设备状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        //搜素完成
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver, filter);
        //开始搜索
        blueadapter.startDiscovery();
    }

    BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "onReceive: " + action);
            if (action == null) return;
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    //获取搜索到设备的信息
                    Log.i(TAG, "device name: " + device.getName() + " address: " + device.getAddress());
                    //获取绑定状态
                    Log.i(TAG, "device bond state : " + device.getBondState());
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    Log.i(TAG, "BOND_STATE_CHANGED device name: " + device.getName() + " address: " + device.getAddress());
                    Log.i(TAG, "BOND_STATE_CHANGED device bond state : " + device.getBondState());
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    Log.i(TAG, "BOND_STATE_CHANGED device name: " + device.getName() + " address: " + device.getAddress());
                    Log.i(TAG, "BOND_STATE_CHANGED device bond state : " + device.getBondState());
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.i(TAG, "bluetooth discovery finished");
                    break;
            }
        }
    };

    private void startScan() {
        if (isConnect) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scanner = blueadapter.getBluetoothLeScanner();
            mScanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build();
            mScanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
//                    System.out.println("onScanResult");
                    if (result.getDevice().getName() != null && result.getDevice().getName().contains(Constant.DEVICE_NAME)) {
                        System.out.println("当前为扫描接收数据");
                        byte[] datas = result.getScanRecord().getBytes();
                        DEVICE_ID = result.getDevice().getAddress().replace(":", "").toLowerCase();
                        String X = String.valueOf(DataUtil.normalHexByteToInt(datas[11]));
                        String Y = String.valueOf(DataUtil.normalHexByteToInt(datas[12]));
                        String Z = String.valueOf(DataUtil.normalHexByteToInt(datas[13]));
                        String D0 = String.valueOf(DataUtil.normalHexByteToInt(datas[14]));
                        String AD = String.valueOf(DataUtil.concat(datas[15], datas[16]));
                        String D4 = String.valueOf(DataUtil.normalHexByteToInt(datas[18]));
                        String D5 = String.valueOf(DataUtil.normalHexByteToInt(datas[19]));
                        String D6 = String.valueOf(DataUtil.normalHexByteToInt(datas[20]));
                        currentDeviceCode = DEVICE_ID;
                        uploadDeviceData(D0, DEVICE_ID, X, Y, Z, AD, D4, D5, D6);
                        //本地传输通知解析
                        BluetoothReceiveBean receiveBean = new BluetoothReceiveBean();
                        receiveBean.setDEVICE_ID(DEVICE_ID);
                        receiveBean.setX(X);
                        receiveBean.setY(Y);
                        receiveBean.setZ(Z);
                        receiveBean.setD0(D0);
                        receiveBean.setAD(AD);
                        receiveBean.setD4(D4);
                        receiveBean.setD5(D5);
                        receiveBean.setD6(D6);
                        EventBus.getDefault().post(new BluetoothReceiveEvent(receiveBean));
                        if (D0.equals("0")) {
                            isDataConnect = false;
                            EventBus.getDefault().post(new BluetoothConnectEvent(0));
                            isConnect = false;
                        }

                        if (!D0.equals("0")) {
                            isDataConnect = true;
                        }

                        if (!isConnect) {
                            connectDevice(result.getDevice());
                        }

                        if (isConnect) {
                            scanner.stopScan(mScanCallback);
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

    private void connectDevice(final BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    super.onConnectionStateChange(gatt, status, newState);
                    if (!isDataConnect) {
                        EventBus.getDefault().post(new BluetoothConnectEvent(0));
                        isConnect = false;
                        return;
                    }
                    switch (status) {
                        case BluetoothGatt.GATT_SUCCESS:
                            EventBus.getDefault().post(new BluetoothConnectEvent(1));
                            isConnect = true;
                            break;

                        case 8:
                            EventBus.getDefault().post(new BluetoothConnectEvent(0));
                            isConnect = false;
                            break;

                        case BluetoothGatt.GATT_FAILURE:
                            EventBus.getDefault().post(new BluetoothConnectEvent(0));
                            isConnect = false;
                            break;
                    }
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        Log.i(TAG, "Connected to GATT server.");
                        // Attempts to discover services after successful connection.
                        //有时候发现服务不回调,需延时 https://stackoverflow.com/questions/41434555/onservicesdiscovered-never-called-while-connecting-to-gatt-server#comment70285228_41526267
                        try {
                            Thread.sleep(600);
                            Log.i(TAG, "Attempting to start service discovery:"
                                    + gatt.discoverServices());
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                    super.onCharacteristicChanged(gatt, characteristic);
                    System.out.println("当前为链接接收数据");
                    byte[] datas = characteristic.getValue();
                    String X = String.valueOf(DataUtil.normalHexByteToInt(datas[11]));
                    String Y = String.valueOf(DataUtil.normalHexByteToInt(datas[12]));
                    String Z = String.valueOf(DataUtil.normalHexByteToInt(datas[13]));
                    String D0 = String.valueOf(DataUtil.normalHexByteToInt(datas[14]));
                    String AD = String.valueOf(DataUtil.concat(datas[15], datas[16]));
                    String D4 = String.valueOf(DataUtil.normalHexByteToInt(datas[18]));
                    String D5 = String.valueOf(DataUtil.normalHexByteToInt(datas[19]));
                    String D6 = String.valueOf(DataUtil.normalHexByteToInt(datas[20]));

                    if ((X.equals("0") && Y.equals("0") && Z.equals("0")) || AD.equals("65535")) {
                        return;
                    }
                    System.out.println("当前为链接接收数据--无脏数据");
                    currentDeviceCode = DEVICE_ID;
                    uploadDeviceData(D0, DEVICE_ID, X, Y, Z, AD, D4, D5, D6);
                    //本地传输通知解析
                    BluetoothReceiveBean receiveBean = new BluetoothReceiveBean();
                    receiveBean.setDEVICE_ID(DEVICE_ID);
                    receiveBean.setX(X);
                    receiveBean.setY(Y);
                    receiveBean.setZ(Z);
                    receiveBean.setD0(D0);
                    receiveBean.setAD(AD);
                    receiveBean.setD4(D4);
                    receiveBean.setD5(D5);
                    receiveBean.setD6(D6);
                    EventBus.getDefault().post(new BluetoothReceiveEvent(receiveBean));
                    if (D0.equals("0")) {
                        isDataConnect = false;
                        EventBus.getDefault().post(new BluetoothConnectEvent(0));
                    }

                    if (!D0.equals("0")) {
                        isDataConnect = true;
                    }
                }


                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    super.onServicesDiscovered(gatt, status);

                    if (status == BluetoothGatt.GATT_SUCCESS) { // 发现蓝牙服务成功
                        List<BluetoothGattService> gattServicesList = gatt.getServices();
                        for (int i = 0; i < gattServicesList.size(); i++) {
                            if (gattServicesList.get(i).getUuid().equals(UUID.fromString(SERVICES_UUID))) { //如果servicesUUID相同，则做如下处理
                                //设置写入特征UUID
//                                BluetoothGattCharacteristic writeCharacteristic = gattServicesList.get(i).getCharacteristic(UUID.fromString(WRITE_UUID));
                                BluetoothGattCharacteristic notifyCharacteristic = gattServicesList.get(i).getCharacteristic(UUID.fromString(NOTIFY_UUID));
                                //开启监听
                                gatt.setCharacteristicNotification(notifyCharacteristic, true);

                                for (BluetoothGattDescriptor dp : notifyCharacteristic.getDescriptors()) {
                                    dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    boolean abc = gatt.writeDescriptor(dp);
                                }
                                break;
                            }

                        }
                    } else {
                        Log.e("onServicesDiscovered", "当前状态:" + status);
                    }
                }
            };
            device.connectGatt(getApplicationContext(), true, mGattCallback);
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e("tag", "device name: " + device.getName() + " address: " + device.getAddress());
                if (device.getName() != null && device.getName().contains(Constant.DEVICE_NAME)) {

                }
            }
        }
    };

    /**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
