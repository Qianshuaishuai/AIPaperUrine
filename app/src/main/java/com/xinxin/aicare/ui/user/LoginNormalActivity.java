package com.xinxin.aicare.ui.user;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.RegisterResponse;
import com.xinxin.aicare.ui.main.MainActivity;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_login_normal)
public class LoginNormalActivity extends BaseActivity {

    private AlertDialog tipDialog;
    private TextView titleTextView;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;

    private String TAG = "permission-checking";
    private static final String[] requestPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.phone)
    private EditText phone;

    @ViewInject(R.id.password)
    private EditText password;

    @ViewInject(R.id.register)
    private TextView register;

    @ViewInject(R.id.forget)
    private TextView forget;

    @Event(R.id.privacy)
    private void privacy(View view){
        Intent intent = new Intent(this,PrivacyActivity.class);
        startActivity(intent);
    }

    @Event(R.id.user_agree)
    private void userAgree(View view){
        Intent intent = new Intent(this,UserAgreeActivity.class);
        startActivity(intent);
    }

    @Event(R.id.login)
    private void loginClick(View view) {
        if (phone.getText().toString().length() != 11) {
            titleTextView.setText("手机号码输入错误， 请重新输入!");
            tipDialog.show();
            return;
        }

        if (password.getText().toString().length() == 0) {
            titleTextView.setText("密码不能为空!");
            tipDialog.show();
            return;
        }

        String rid = ((PaperUrineApplication) getApplication()).getRid();
        if (TextUtils.isEmpty(rid)) {
            rid = "hudatech";
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_LOGINBYPSW);
        params.addQueryStringParameter("PHONE", phone.getText().toString());
        params.addQueryStringParameter("PSW", password.getText().toString());
        params.addQueryStringParameter("RID", rid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegisterResponse response = gson.fromJson(result, RegisterResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        T.s("登录成功");
                        ((PaperUrineApplication) getApplication()).saveUserInfo(response.getData());
                        startMainActivity();
                        break;

                    default:
                        titleTextView.setText("帐号或密码错误");
                        tipDialog.show();
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Event(R.id.wechat)
    private void wechatClick(View view) {
        T.s("not wechat");
    }

    @Event(R.id.code_login)
    private void codeLoginClick(View view) {
        Intent newIntent = new Intent(this, LoginActivity.class);
        startActivity(newIntent);
    }

    @Event(R.id.register)
    private void registerClick(View view) {
        Intent newIntent = new Intent(this, RegisterActivity.class);
        startActivity(newIntent);
    }

    @Event(R.id.forget)
    private void forgetClick(View view) {
        Intent newIntent = new Intent(this, ForgetActivity.class);
        startActivity(newIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTipDialog();
        initView();
        requestPermission();
    }

    private void initData() {
        UserBean userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        if (userBean != null && !TextUtils.isEmpty(userBean.getONLINE_ID())) {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        register.getPaint().setAntiAlias(true);//抗锯齿

        forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forget.getPaint().setAntiAlias(true);//抗锯齿
    }

    private void initTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_common_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        tipDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        titleTextView = (TextView) view.findViewById(R.id.title);
        titleTextView.setText("手机号码输入错误， 请重新输入!");
        Button commonButton = (Button) view.findViewById(R.id.button);

        commonButton.setText("确认");

        commonButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tipDialog.cancel();
            }
        });

        tipDialog.setCancelable(false);
//        tipDialog.show();
    }

    private void requestPermission() {

        Log.i(TAG,"requestPermission");
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG,"checkSelfPermission");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.i(TAG,"shouldShowRequestPermissionRationale");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                Log.i(TAG,"requestPermissions");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG,"onRequestPermissionsResult granted");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initData();
                } else {
                    Log.i(TAG,"onRequestPermissionsResult denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showWaringDialog();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showWaringDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->AI育婴->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                        finish();
                    }
                }).show();
    }
}
