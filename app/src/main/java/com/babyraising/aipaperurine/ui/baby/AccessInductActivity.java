package com.babyraising.aipaperurine.ui.baby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_access_induct)
public class AccessInductActivity extends BaseActivity {

    private String memberId = "";

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.next)
    private void next(View view) {
        Intent intent = new Intent(this, MoblieInductActivity.class);
        intent.putExtra("memberId",memberId);
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
    }
}
