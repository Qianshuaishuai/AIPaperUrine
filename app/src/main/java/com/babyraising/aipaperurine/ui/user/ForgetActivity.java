package com.babyraising.aipaperurine.ui.user;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

@ContentView(R.layout.activity_forget)
public class ForgetActivity extends BaseActivity {

    private AlertDialog tipDialog;
    private TextView titleTextView;

    private int codeNotClickTime = 0;
    private Timer timer;
    private TimerTask timerTask;

    @ViewInject(R.id.phone)
    private EditText phone;

    @ViewInject(R.id.code)
    private EditText code;

    @ViewInject(R.id.password)
    private EditText password;

    @ViewInject(R.id.sure_password)
    private EditText surePassword;

    @ViewInject(R.id.send_code)
    private TextView sendCode;

    @Event(R.id.layout_back)
    private void backClick(View view){
        finish();
    }

    @Event(R.id.commit)
    private void commitClick(View view){
        if (phone.getText().toString().length() != 11) {
            titleTextView.setText("手机号码格式不对");
            tipDialog.show();
            return;
        }

        if (password.getText().toString().length() == 0) {
            titleTextView.setText("新密码不能为空");
            tipDialog.show();
            return;
        }

        if (!password.getText().toString().equals(surePassword.getText().toString())) {
            titleTextView.setText("两次密码不一样");
            tipDialog.show();
            return;
        }

        if (code.getText().toString().length() == 0) {
            titleTextView.setText("请填写验证码");
            tipDialog.show();
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITPSW);
        params.addQueryStringParameter("PHONE", phone.getText().toString());
        params.addQueryStringParameter("CODE", code.getText().toString());
        params.addQueryStringParameter("PSW", password.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    default:
                        titleTextView.setText("修改失败");
                        break;
                    case 0:
                        T.s("修改成功");
                        finish();
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

    @Event(R.id.send_code)
    private void sendCodeClick(View view){
        if (phone.getText().toString().length() != 11) {
            titleTextView.setText("手机号码格式不对");
            tipDialog.show();
            return;
        }
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GET_CODE);
        params.addQueryStringParameter("PHONE", phone.getText().toString());
        params.addQueryStringParameter("BSTYPE", "e");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 555:
                        titleTextView.setText("手机号不存在");
                        tipDialog.show();
                        break;
                    case 666:
                        titleTextView.setText("手机号已注册");
                        tipDialog.show();
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
                            sendCode.setText("发送验证码");
                            sendCode.setTextColor(getResources().getColor(R.color.colorCommon));
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initTipDialog();
    }
}
