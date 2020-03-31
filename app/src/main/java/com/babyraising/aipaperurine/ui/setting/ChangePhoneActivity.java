package com.babyraising.aipaperurine.ui.setting;

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
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
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

@ContentView(R.layout.activity_change_phone)
public class ChangePhoneActivity extends BaseActivity {

    private UserBean userBean;
    private String changeType = "";

    private AlertDialog tipDialog;
    private TextView titleTextView;

    private int codeNotClickTime = 0;
    private Timer timer;
    private TimerTask timerTask;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @ViewInject(R.id.current_phone)
    private TextView current_phone;

    @ViewInject(R.id.input_phone)
    private EditText input_phone;

    @ViewInject(R.id.input_code)
    private EditText input_code;

    @ViewInject(R.id.send_code)
    private Button send_code;

    @Event(R.id.send_code)
    private void sendCode(View view) {
        if (input_phone.getText().toString().length() != 11) {
            titleTextView.setText("新手机号码格式不正确");
            tipDialog.show();
            return;
        }
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GET_CODE);
        params.addQueryStringParameter("PHONE", input_phone.getText().toString());
        params.addQueryStringParameter("BSTYPE", "b");
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
        send_code.setClickable(false);
        codeNotClickTime = 60;
        send_code.setTextColor(Color.GRAY);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        send_code.setText(codeNotClickTime + "秒");
                        codeNotClickTime--;
                        if (codeNotClickTime == 0) {
                            timer.cancel();
                            send_code.setClickable(true);
                            send_code.setText("发送验证码");
                            send_code.setTextColor(getResources().getColor(R.color.colorCommon));
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    @Event(R.id.commit)
    private void commit(View view) {

        if (input_phone.getText().length() != 11) {
            T.s("更换手机号需是11位");
            return;
        }

        if (input_code.getText().length() <= 0) {
            T.s("请输入验证码");
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_BINDPHONE);
        params.addQueryStringParameter("APPUESR_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("PHONE", userBean.getPHONE());
        params.addQueryStringParameter("CODE", input_code.getText().toString());
        params.addQueryStringParameter("TYPE", changeType);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegisterResponse response = gson.fromJson(result, RegisterResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("更换成功");
                        finish();
                        break;

                    default:
                        T.s("更换失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTipDialog();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        current_phone.setText(userBean.getPHONE());
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
}
