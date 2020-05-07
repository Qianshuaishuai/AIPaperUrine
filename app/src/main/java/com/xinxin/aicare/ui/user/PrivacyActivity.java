package com.xinxin.aicare.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_privacy)
public class PrivacyActivity extends BaseActivity {

    @ViewInject(R.id.main)
    private TextView main;

    @Event(R.id.layout_back)
    private void backClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
