package com.xinxin.aicare.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.PersonBean;
import com.xinxin.aicare.bean.UserBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_account)
public class AccountActivity extends BaseActivity {

    private UserBean userBean;
    private PersonBean personBean;

    @ViewInject(R.id.phone)
    private TextView phone;

    @ViewInject(R.id.tv_password_status)
    private TextView tvPasswordStatus;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.layout_phone)
    private void layoutPhone(View view) {
        Intent intent = new Intent(this, ChangePhoneActivity.class);
        startActivity(intent);
    }

    @Event(R.id.layout_password)
    private void layoutPassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        personBean = ((PaperUrineApplication) getApplication()).getPersonInfo();

        phone.setText(userBean.getPHONE());

        switch (userBean.getIS_PSW()) {
            case "0":
                tvPasswordStatus.setText("未设置");
                break;
            case "1":
                tvPasswordStatus.setText("已设置");
                break;
        }
    }
}
