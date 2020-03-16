package com.babyraising.aipaperurine.ui.user;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.response.CommonResponse;
import com.babyraising.aipaperurine.response.RegisterResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_login_third)
public class LoginThirdActivity extends BaseActivity {

    private int codeNotClickTime = 0;
    private Timer timer;
    private TimerTask timerTask;

    @ViewInject(R.id.phone)
    private EditText phone;

    @ViewInject(R.id.code)
    private EditText code;

    @ViewInject(R.id.send_code)
    private Button sendCode;

    @Event(R.id.send_code)
    private void sendCodeClick(View view) {
        if (phone.getText().toString().length() != 11) {
            T.s("手机号码格式不对");
            return;
        }
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GET_CODE);
        params.addQueryStringParameter("PHONE", phone.getText().toString());
        params.addQueryStringParameter("BSTYPE", "rl");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 555:
                        T.s("手机号不存在");
                        break;
                    case 666:
                        T.s("手机号已注册");
                        break;
                    case 0:
                        T.s("发送成功");
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
        sendCode.setClickable(false);
        codeNotClickTime = 60;
        sendCode.setTextColor(Color.GRAY);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendCode.setText(codeNotClickTime + "秒");
                        codeNotClickTime--;
                        if (codeNotClickTime == 0) {
                            timer.cancel();
                            sendCode.setClickable(true);
                            sendCode.setText("获取验证码");
                            sendCode.setTextColor(getResources().getColor(R.color.colorCommon));
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    @Event(R.id.login)
    private void loginClick(View view) {
        if (phone.getText().toString().length() != 11) {
            T.s("手机号码格式不对");
            return;
        }

        if (code.getText().toString().length() == 0) {
            T.s("请填写验证码");
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_LOGINWX);
        params.addQueryStringParameter("PHONE", phone.getText().toString());
        params.addQueryStringParameter("CODE", code.getText().toString());
        params.addQueryStringParameter("RID", Constant.RID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                RegisterResponse response = gson.fromJson(result, RegisterResponse.class);
                switch (response.getResult()) {
                    case 7:
                        T.s("验证码错误或已过期");
                        break;
                    case 11:
                        T.s("手机号已注册");
                        break;
                    case 0:
                        T.s("注册成功");
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

    @Event(R.id.register_layout)
    private void registerLayoutClick(View view) {

    }

    @Event(R.id.bind_phone)
    private void bindPhoneClick(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
