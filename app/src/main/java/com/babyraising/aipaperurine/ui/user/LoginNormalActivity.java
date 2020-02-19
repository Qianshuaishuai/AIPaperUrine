package com.babyraising.aipaperurine.ui.user;

import android.app.AlertDialog;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login_normal)
public class LoginNormalActivity extends BaseActivity {

    private AlertDialog tipDialog;

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

    }

    @Event(R.id.wechat)
    private void wechatClick(View view) {

    }

    @Event(R.id.code_login)
    private void codeLoginClick(View view) {

    }

    @Event(R.id.register)
    private void registerClick(View view) {

    }

    @Event(R.id.forget)
    private void forgetClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTipDialog();
        initView();
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
        TextView titleTextView = (TextView) view.findViewById(R.id.title);
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
