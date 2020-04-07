package com.babyraising.aipaperurine.ui.info;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_sleep_posture)
public class SleepPostureActivity extends BaseActivity {

    @Event(R.id.layout_back)
    private void layoutBack(View view){
        finish();
    }

    @ViewInject(R.id.middle_iv)
    private ImageView ivMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        int commonHeight = 1776;
        int scale = height / commonHeight;
        int offsetHeight = 400 * scale;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivMiddle.getLayoutParams();
        params.topMargin = offsetHeight;
        ivMiddle.setLayoutParams(params);
    }
}
