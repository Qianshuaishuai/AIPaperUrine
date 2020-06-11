package com.xinxin.aicare.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_pay_result)
public class PayResultActivity extends BaseActivity {

    private String REALPAY = "0.0";

    @ViewInject(R.id.real_price)
    private TextView price;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.go_on)
    private void goOn(View view) {
        finish();
    }

    @Event(R.id.sure)
    private void sure(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        REALPAY = intent.getStringExtra("realPay");

        price.setText("实际付款  ￥" + REALPAY);
    }
}
