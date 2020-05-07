package com.xinxin.aicare.ui.user;

import android.os.Bundle;
import android.view.View;

import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_user_agree)
public class UserAgreeActivity extends BaseActivity {

    @Event(R.id.layout_back)
    private void backClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
