package com.xinxin.aicare.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.UserGrowthRankingResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;
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

@ContentView(R.layout.activity_rank_share)
public class RankShareActivity extends BaseActivity {

    private UserBean bean;

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.all_rank)
    private TextView all_rank;

    @ViewInject(R.id.week_rank)
    private TextView week_rank;

    @ViewInject(R.id.all_change_urine)
    private TextView all_change_urine;

    @ViewInject(R.id.all_volume)
    private TextView all_volume;

    @ViewInject(R.id.all_sign)
    private TextView all_sign;

    @ViewInject(R.id.commit)
    private TextView commit;

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
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fenxiang/" + "rank-share.png";
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
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fenxiang/" + "rank-share.png";
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

        initView();
        initData();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getApplication()).getUserInfo();

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_USERGROWTHRANKING);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("ID", bean.getAPPUSER_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                UserGrowthRankingResponse response = gson.fromJson(result, UserGrowthRankingResponse.class);
                switch (response.getResult()) {
                    case 0:
                        ImageOptions options = new ImageOptions.Builder().
                                setRadius(DensityUtil.dip2px(56)).setCrop(true).build();
                        x.image().bind(icon, response.getData().getHEADIMG(), options);
                        name.setText(response.getData().getNICKNAME());
                        time.setText(response.getData().getJOINDATE() + "-" + response.getData().getCURDATE());
                        all_rank.setText(response.getData().getMyGrowthRanking());
                        week_rank.setText(response.getData().getMyWeekGrowthRanking());
                        all_sign.setText(response.getData().getSIGNIN_ALL());
                        all_change_urine.setText(response.getData().getDIAPER_ALL());
                        all_volume.setText(response.getData().getURINE_VOLUME_ALL());
                        commit.setText(response.getData().getTIP());
                        break;
                    default:
                        T.s("获取排名分享信息失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误处理:" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {

    }
}
