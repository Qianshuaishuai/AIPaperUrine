package com.babyraising.aipaperurine.ui.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.RegisterResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    private UserBean userBean;
    private AlertDialog tipDialog;
    private TextView titleTextView;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.layout_user)
    private void layoutUser(View view) {
        Intent intent = new Intent(this, ChangePhoneActivity.class);
        startActivity(intent);
    }

    @Event(R.id.layout_clear_cache)
    private void layoutClearCache(View view) {
        finish();
    }

    @Event(R.id.layout_advice)
    private void layoutAdvice(View view) {
        Intent intent = new Intent(this, AdviceActivity.class);
        startActivity(intent);
    }

    @Event(R.id.layout_about)
    private void layoutAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Event(R.id.layout_version)
    private void layoutVersion(View view) {
        Intent intent = new Intent(this, VersionActivity.class);
        startActivity(intent);
    }

    @Event(R.id.logout)
    private void logout(View view) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_LOGOUT);
        params.addQueryStringParameter("APPUESR_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegisterResponse response = gson.fromJson(result, RegisterResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("登出成功");
                        break;

                    default:
                        titleTextView.setText("登出失败");
                        tipDialog.show();
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
