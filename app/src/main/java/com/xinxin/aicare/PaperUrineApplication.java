package com.xinxin.aicare;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.xinxin.aicare.bean.PersonBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.x;

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

    public void savePersonInfo(PersonBean bean) {
        String beanString = gson.toJson(bean);
        editor.putString("personInfo", beanString);
        editor.commit();
    }

    public PersonBean getPersonInfo() {
        return gson.fromJson(sp.getString("personInfo", ""), PersonBean.class);
    }
}
