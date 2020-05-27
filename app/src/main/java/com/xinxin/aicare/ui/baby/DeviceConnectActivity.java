package com.xinxin.aicare.ui.baby;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lixs.charts.Utils;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.ble.BleWrapper;
import com.xinxin.aicare.ble.BleWrapperUiCallbacks;
import com.xinxin.aicare.event.BindSuccessEvent;
import com.xinxin.aicare.event.PayResultEvent;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.service.BluetoothBindService;
import com.xinxin.aicare.service.BluetoothService;
import com.xinxin.aicare.util.DataUtil;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.aigestudio.datepicker.utils.DataUtils;

@ContentView(R.layout.activity_device_connect)
public class DeviceConnectActivity extends BaseActivity {

    private UserBean userBean;
    private String memberId = "";
    private BluetoothAdapter blueadapter;
    private AlertDialog boothDialog;

    @Event(R.id.layout_back)
    private void back(View view) {
        tipDialog.show();
    }

    @ViewInject(R.id.layout1)
    private RelativeLayout layout1;

    @ViewInject(R.id.layout2)
    private RelativeLayout layout2;

    @ViewInject(R.id.layout3)
    private RelativeLayout layout3;

    @ViewInject(R.id.layout4)
    private RelativeLayout layout4;

    @ViewInject(R.id.layout5)
    private RelativeLayout layout5;

    private AlertDialog tipDialog;
    private Timer timer;
    private TimerTask timerTask;
    private Timer timer1;
    private TimerTask timerTask1;

    private BleWrapper mBleWrapper = null;

    private int mode = 1;
    private boolean isUpload = false;
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initBoothDialog();
        initTipDialog();
        initBooth();
        EventBus.getDefault().register(this);
//        initBluetooth();
    }

    private void initBooth() {
        blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (blueadapter == null) {
            //表示手机不支持蓝牙
            T.s("该手机版本不支持BLE蓝牙");
            finish();
            return;
        }

        if (!blueadapter.isEnabled()) {//如果没打开，则打开蓝牙
            boothDialog.show();
            return;
        }

        if (bleCheck()) {
            service = new Intent(this, BluetoothBindService.class);
            service.putExtra("userId", userBean.getAPPUSER_ID());
            service.putExtra("onlineId", userBean.getONLINE_ID());
            service.putExtra("memberId", memberId);
            startService(service);
        } else {
            T.s("当前手机版本不支持BLE设备绑定");
            finish();
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

    private boolean bleCheck() {
        boolean result = false;
        if (getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            result = true;
        }
        return result;
    }

    private void initView() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (mode) {
                            case 1:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.GONE);
                                layout3.setVisibility(View.GONE);
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 2:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.GONE);
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 3:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 4:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.VISIBLE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 5:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.VISIBLE);
                                layout1.setVisibility(View.VISIBLE);
                                break;
                        }


                        if (mode == 5) {
                            mode = 1;
                        } else {
                            mode = mode + 1;
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 350);
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        blueadapter.cancelDiscovery();
//        unregisterReceiver(bluetoothReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BindSuccessEvent event) {
        System.out.println("收到绑定成功通知");
        stopService(service);
        finish();
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
                        T.s("上传设备信息成功");
                        finish();
                        break;
                    case 2:
                        T.s(response.getMsg());
                        break;
                    default:
                        T.s("上传设备信息失败");
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

    private void initTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        tipDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);
        TextView title = (TextView) view.findViewById(R.id.title);

        title.setText("确定要退出设备连接?");

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tipDialog.cancel();
            }
        });

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tipDialog.cancel();
                finish();
            }
        });

        tipDialog.setCancelable(false);
//        tipDialog.show();
    }

    private void stopDiscovery() {
        blueadapter.cancelDiscovery();
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

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName() != null && device.getName().contains(Constant.DEVICE_NAME)) {
                    timer1.cancel();
                    T.s("已找到尿湿感应器蓝牙设备，正在连接中!");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                        boolean isBond = device.createBond();
//                        if (isBond) {
//                            T.s("正尝试配对连接!");
//                        }

                    } else {
                        T.s("当前android版本不支持自动配对蓝牙设备");
                    }
                }
            }
        }
    };

    BroadcastReceiver bondReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = null;
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            }

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //表示配对信息
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        T.s("正在配对中");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        T.s("配对成功");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            device.connectGatt(context, false, new BluetoothGattCallback() {
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
                                    System.out.println("newState:" + newState);
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
                            });
                        }
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对
                        T.s("取消配对");
                        break;
                }
            }
        }

    };


    /**
     * 蓝牙连接广播
     */
    public static class BlueToothConnectReceiver extends BroadcastReceiver {

        private OnBleConnectListener onBleConnectListener;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (action) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    if (onBleConnectListener != null) {
                        onBleConnectListener.onConnect(device);
                    }
                    System.out.println("蓝牙已连接：" + device.getName());
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    if (onBleConnectListener != null) {
                        onBleConnectListener.onDisConnect(device);
                    }
                    System.out.println("蓝牙已断开：" + device.getName());
                    break;
            }
        }

        public interface OnBleConnectListener {
            void onConnect(BluetoothDevice device);

            void onDisConnect(BluetoothDevice device);
        }

        public void setOnBleConnectListener(OnBleConnectListener onBleConnectListener) {
            this.onBleConnectListener = onBleConnectListener;
        }
    }

    /**
     * 判断是否支持蓝牙，并打开蓝牙
     * 获取到BluetoothAdapter之后，还需要判断是否支持蓝牙，以及蓝牙是否打开。
     * 如果没打开，需要让用户打开蓝牙：
     */
    private boolean checkBleDevice() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //首先获取BluetoothManager
            BluetoothManager bluetoothManager =
                    (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            //获取BluetoothAdapter
            if (bluetoothManager != null) {
                BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
                if (mBluetoothAdapter != null) {
                    if (!mBluetoothAdapter.isEnabled()) {
                        //调用enable()方法直接打开蓝牙
                        if (!mBluetoothAdapter.enable()) {
                            Log.i("tag", "蓝牙打开失败");
                        } else {
                            Log.i("tag", "蓝牙已打开");
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    Log.i("tag", "同意申请");
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    Runnable timeout;
    private Handler mHandler = new Handler();

    void bind(final String sn, boolean isForce) {
        System.out.println(sn);
    }

    public void scan() {
        if (checkBleDevice()) {
            T.s("扫描");
            if (isScan) {
                return;
            }
            sn = null;
            isScan = true;
            rssi = -1000;
            mBleWrapper.startScanning();
            timeout = new Runnable() {
                @Override
                public void run() {
                    isScan = false;
                    if (sn == null) {
                        T.s("没有找到设备");
                    } else {
                        bind(sn, false);
                    }
                    mBleWrapper.stopScanning();
                }
            };
            mHandler.postDelayed(timeout, 4000);
        } else {
            T.s("扫描警告");
        }
        //mHandler.removeCallbacks(timeout);
    }

    private void initBluetooth() {
        T.s("初始化设备");
        // create BleWrapper with empty callback object except uiDeficeFound function (we need only that here)
        mBleWrapper = new BleWrapper(this, new BleWrapperUiCallbacks.Null() {
            @Override
            public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
                T.s("搜索到设备:" + device.getName());
                handleFoundDevice(device, rssi, record);
            }
        });

        // check if we have BT and BLE on board
        if (mBleWrapper.checkBleHardwareAvailable() == false) {
            T.s("bleMissing");
            bleMissing();
        }
        mBleWrapper.initialize();
    }

    int rssi = -1000;
    boolean isScan = false;
    public String sn = null;

    private void handleFoundDevice(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
        if (rssi < -70 || rssi < this.rssi) {
            return;
        }
        if (scanRecord[0] == 2 && scanRecord[1] == 1 && scanRecord[2] == 6) {
            String sn = DataUtil.bytes2HexString(scanRecord, 5, 6);
            this.rssi = rssi;
            this.sn = sn;
        }

        //mBleWrapper.stopScanning();
        //mHandler.removeCallbacks(timeout);
    }

    private void btDisabled() {
        Toast.makeText(this, "Sorry, BT has to be turned ON for us to work!", Toast.LENGTH_LONG).show();
    }

    private void bleMissing() {
        Toast.makeText(this, "BLE Hardware is required but not available!", Toast.LENGTH_LONG).show();
    }
}
