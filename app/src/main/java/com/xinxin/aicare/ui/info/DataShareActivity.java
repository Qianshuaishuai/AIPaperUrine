package com.xinxin.aicare.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.MemberDataShareBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.MemberDataCal4Response;
import com.xinxin.aicare.response.MemberDataShareResponse;
import com.xinxin.aicare.util.T;
import com.xinxin.aicare.util.ViewUtils;
import com.xinxin.aicare.util.WxShareUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_data_share)
public class DataShareActivity extends BaseActivity {

    private UserBean userBean;
    private String memberId;
    private String STARTDATE;
    private String ENDDATE;

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.average)
    private TextView average;

    @ViewInject(R.id.common_average)
    private TextView commonAverage;

    @ViewInject(R.id.volume)
    private TextView volume;

    @ViewInject(R.id.common_volume)
    private TextView commonVolume;

    @ViewInject(R.id.night_count)
    private TextView nightCount;

    @ViewInject(R.id.common_night_count)
    private TextView commonNightCount;

    @ViewInject(R.id.layout_main)
    private RelativeLayout layoutMain;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @ViewInject(R.id.layout_share_all)
    private RelativeLayout layoutShare;

    @Event(R.id.layout_share_1)
    private void share1Click(View view) {
        try {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fenxiang/" + "data-share.png";
            ViewUtils.saveView(this, layoutMain, dir);
            WxShareUtils.imageShare(this, dir, 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            T.s("获取分享信息失败");
        }
        layoutShare.setVisibility(View.GONE);
    }

    @Event(R.id.layout_share_2)
    private void share2Click(View view) {
        try {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fenxiang/" + "data-share.png";
            ViewUtils.saveView(this, layoutMain, dir);
            WxShareUtils.imageShare(this, dir, 2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            T.s("获取分享信息失败");
        }
        layoutShare.setVisibility(View.GONE);
    }

    @Event(R.id.share)
    private void share(View view) {
        layoutShare.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");
        STARTDATE = intent.getStringExtra("startdate");
        ENDDATE = intent.getStringExtra("enddate");

        if (TextUtils.isEmpty(memberId)) {
            T.s("获取宝宝数据失败");
            finish();
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERDATASHARE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberId);
        params.addQueryStringParameter("STARTDATE", STARTDATE);
        params.addQueryStringParameter("ENDDATE", ENDDATE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                System.out.println(result);
                MemberDataShareResponse response = gson.fromJson(result, MemberDataShareResponse.class);
                switch (response.getResult()) {
                    case 0:
                        updateView(response.getData());
                        break;
                    default:
                        T.s("获取宝宝数据分享失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex);
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void updateView(MemberDataShareBean data) {
        ImageOptions options = new ImageOptions.Builder().
                setRadius(DensityUtil.dip2px(56)).setCrop(true).build();
        x.image().bind(icon, data.getHEADIMG(), options);
        name.setText(data.getNICKNAME());
        time.setText(STARTDATE + "-" + ENDDATE);
        commonAverage.setText("用户平均使用片数：" + data.getAVG_DIAPER_CNT() + "片");
        if (!TextUtils.isEmpty(data.getNIGHT_DIAPER_CNT())) {
            nightCount.setText(data.getNIGHT_DIAPER_CNT());
        } else {
            nightCount.setText("-");
        }

        if (!TextUtils.isEmpty(data.getDIAPER_CNT())) {
            average.setText(data.getDIAPER_CNT());
        } else {
            average.setText("-");
        }

        if (!TextUtils.isEmpty(data.getVOLUME())) {
            volume.setText(data.getVOLUME());
        } else {
            volume.setText("-");
        }
        commonNightCount.setText("用户平均使用片数：" + data.getAVG_NIGHT_DIAPER_CNT() + "次");
    }
}
