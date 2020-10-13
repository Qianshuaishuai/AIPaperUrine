package com.xinxin.aicare.ui.baby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_access_induct)
public class AccessInductActivity extends BaseActivity {

    private String memberId = "";

    @ViewInject(R.id.introduce_one)
    private ImageView introduceOne;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.next)
    private void next(View view) {
        Intent intent = new Intent(this, MoblieInductActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");

        Glide.with(this).load(R.drawable.introduce_one).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(introduceOne);
    }
}
