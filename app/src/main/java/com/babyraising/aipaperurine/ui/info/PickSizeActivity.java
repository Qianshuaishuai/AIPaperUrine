package com.babyraising.aipaperurine.ui.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_pick_size)
public class PickSizeActivity extends BaseActivity {

    @ViewInject(R.id.group_brand)
    private RadioGroup brandGroup;

    @ViewInject(R.id.iv_brand_more)
    private ImageView ivBrandMore;

    @Event(R.id.layout_brand)
    private void brandLayoutClick(View view) {
        if (brandGroup.getVisibility() == View.VISIBLE) {
            brandGroup.setVisibility(View.GONE);
            ivBrandMore.setImageResource(R.mipmap.iconmonstr_right_chakanxiangqing);
        } else {
            brandGroup.setVisibility(View.VISIBLE);
            ivBrandMore.setImageResource(R.mipmap.btn_xiala_pinpaixuanze);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

    }
}
