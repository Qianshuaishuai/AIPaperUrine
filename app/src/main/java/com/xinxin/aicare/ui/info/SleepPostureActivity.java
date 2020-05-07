package com.xinxin.aicare.ui.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.util.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_sleep_posture)
public class SleepPostureActivity extends BaseActivity {

    private String posture = "";

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @ViewInject(R.id.middle_iv)
    private ImageView ivMiddle;

    @ViewInject(R.id.middle_tv)
    private TextView tvMiddle;

    @ViewInject(R.id.pos_iv1)
    private ImageView posIv1;

    @ViewInject(R.id.pos_tv1)
    private TextView posTv1;

    @ViewInject(R.id.pos_iv2)
    private ImageView posIv2;

    @ViewInject(R.id.pos_tv2)
    private TextView posTv2;

    @ViewInject(R.id.pos_iv3)
    private ImageView posIv3;

    @ViewInject(R.id.pos_tv3)
    private TextView posTv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        posture = intent.getStringExtra("posture");
        if (TextUtils.isEmpty(posture)) {
            T.s("未获取到当前宝宝的睡姿");
            return;
        }

        switch (posture) {
            case "1":
                ivMiddle.setImageResource(R.mipmap.img_baobaopashui_xiao);
                tvMiddle.setText("宝宝趴睡");
                break;
            case "2":
                ivMiddle.setImageResource(R.mipmap.img_baobaoyoutang_xiao);
                tvMiddle.setText("宝宝右躺");
                break;
            case "4":
                ivMiddle.setImageResource(R.mipmap.img_baobaoyangshui_xiao);
                tvMiddle.setText("宝宝仰睡");
                break;
            case "8":
                ivMiddle.setImageResource(R.mipmap.img_baobaozuotang_xiao);
                tvMiddle.setText("宝宝左躺");
                break;
            case "16":

                break;
            case "32":

                break;
        }
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
