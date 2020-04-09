package com.babyraising.aipaperurine.ui.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_refund)
public class RefundActivity extends BaseActivity {

    private UserBean userBean;
    private String yuyueId = "";

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.simple_count)
    private TextView simpleCount;

    @ViewInject(R.id.rb_receive_1)
    private RadioButton rbReceive1;

    @ViewInject(R.id.rb_receive_2)
    private RadioButton rbReceive2;

    @ViewInject(R.id.group_receive)
    private RadioGroup rGreceive;

    @ViewInject(R.id.price)
    private TextView price;

    @ViewInject(R.id.rv_select_reason)
    private RecyclerView reSelectReason;

    @ViewInject(R.id.rv_refund_photo)
    private RecyclerView rvRefundPhoto;

    @ViewInject(R.id.sign_txt)
    private EditText signTxt;

    @ViewInject(R.id.sign_count)
    private TextView signCount;

    @ViewInject(R.id.reason)
    private TextView reason;

    @Event(R.id.reason_layout)
    private void reasonLayoutClick(View view) {

    }

    @Event(R.id.commit)
    private void commit(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        yuyueId = intent.getStringExtra("yuyueId");

    }

    private void initView() {
        rGreceive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
    }
}
