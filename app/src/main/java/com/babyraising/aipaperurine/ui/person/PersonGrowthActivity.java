package com.babyraising.aipaperurine.ui.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.GrowthAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.GrowthPointBean;
import com.babyraising.aipaperurine.bean.MyGrowthBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.MyGrowthResponse;
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

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_person_growth)
public class PersonGrowthActivity extends BaseActivity {

    private UserBean userBean;
    private MyGrowthBean growthBean;

    private GrowthAdapter growthAdapter;
    private List<GrowthPointBean> growthList;

    @Event(R.id.layout_back)
    private void layoutBackClick(View view) {
        finish();
    }

    @Event(R.id.more)
    private void more(View view) {
        Intent intent = new Intent(this, GrowthActivity.class);
        startActivity(intent);
    }

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.growth_count)
    private TextView growth_count;

    @ViewInject(R.id.rank)
    private TextView rank;

    @ViewInject(R.id.growth_level)
    private TextView growth_level;

    @ViewInject(R.id.rv_growth)
    private RecyclerView rv_growth;

    @ViewInject(R.id.rv_sign_medal)
    private RecyclerView rv_sign_medal;

    @ViewInject(R.id.rv_growth_medal)
    private RecyclerView rv_growth_medal;

    @ViewInject(R.id.rv_change_medal)
    private RecyclerView rv_change_medal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MYGROWTH);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MyGrowthResponse response = gson.fromJson(result, MyGrowthResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        growthBean = response.getData();
                        updateView();
                        break;
                    default:
                        T.s("获取我的成长记录失败");
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

    private void updateView() {
        ImageOptions options = new ImageOptions.Builder().
                setRadius(DensityUtil.dip2px(60)).build();
        x.image().bind(icon, growthBean.getHEADIMG(), options);

        name.setText(growthBean.getNICKNAME());
        growth_count.setText(growthBean.getGROWTH());
        rank.setText(growthBean.getMyGrowthRanking());
        growth_level.setText(growthBean.getNEED_GROWTH());

        growthList.clear();
        for (int a = 0; a < growthBean.getLIST().size(); a++) {
            growthList.add(growthBean.getLIST().get(a));
        }

        growthAdapter.notifyDataSetChanged();
    }

    private void initView() {
        growthList = new ArrayList<>();
        growthAdapter = new GrowthAdapter(growthList);
        LinearLayoutManager growthManager = new LinearLayoutManager(this);

        rv_growth.setAdapter(growthAdapter);
        rv_growth.setLayoutManager(growthManager);
        growthAdapter.setOnItemClickListener(new GrowthAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }
}
