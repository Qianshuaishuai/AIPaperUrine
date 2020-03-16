package com.babyraising.aipaperurine.ui.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.response.RegisterResponse;
import com.babyraising.aipaperurine.ui.main.MainActivity;
import com.babyraising.aipaperurine.util.T;
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

    @Event(R.id.login)
    private void loginClick(View view) {
        phone.setText("186737007151");
        password.setText("1111");
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

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_LOGINBYPSW);
        params.addQueryStringParameter("PHONE", phone.getText().toString());
        params.addQueryStringParameter("PSW", password.getText().toString());
        params.addQueryStringParameter("RID", Constant.RID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegisterResponse response = gson.fromJson(result, RegisterResponse.class);
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
}
