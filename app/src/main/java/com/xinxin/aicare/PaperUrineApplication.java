package com.xinxin.aicare;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import com.xinxin.aicare.bean.PersonBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.service.BluetoothService;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.ups.JPushUPSManager;
import cn.jpush.android.ups.TokenResult;
import cn.jpush.android.ups.UPSRegisterCallBack;

public class PaperUrineApplication extends Application {
    private String TAG = "BlackFruitVipApplication";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        T.init(this);
        if (Constant.DEBUG) {
            Log.d(TAG, "init Xuitils");
        }

        initSp();

        if (bleCheck()) {
            System.out.println("已开启上传设备数据服务");
            Intent intent = new Intent(this, BluetoothService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
//        JPushInterface.init(getApplicationContext());//init初始化SDK
        JPushUPSManager.registerToken(getApplicationContext(), "741c8ea2604a019c52227f02", "", "", new UPSRegisterCallBack() {
            @Override
            public void onResult(TokenResult tokenResult) {

            }
        });
        JPushInterface.setDebugMode(true);//设置调试模式
        JPushInterface.init(getApplicationContext());

        //获取RegistrationID唯一标识
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        saveRid(rid);
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

    private void initSp() {
        sp = getSharedPreferences("prod", Context.MODE_PRIVATE);
        editor = sp.edit();
        gson = new Gson();
    }

    public void saveUserInfo(UserBean bean) {
        String beanString = gson.toJson(bean);
        editor.putString("info", beanString);
        editor.commit();
    }

    public UserBean getUserInfo() {
        return gson.fromJson(sp.getString("info", ""), UserBean.class);
    }

    public void saveRid(String rid) {
        editor.putString("rid", rid);
        editor.commit();
    }

    public String getRid() {
        return sp.getString("rid", "");
    }


    public void savePersonInfo(PersonBean bean) {
        String beanString = gson.toJson(bean);
        editor.putString("personInfo", beanString);
        editor.commit();
    }

    public PersonBean getPersonInfo() {
        return gson.fromJson(sp.getString("personInfo", ""), PersonBean.class);
    }
}
