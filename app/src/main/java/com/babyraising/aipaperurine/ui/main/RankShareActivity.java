package com.babyraising.aipaperurine.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.UserGrowthRankingResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

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

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
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
