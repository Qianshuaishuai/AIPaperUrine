package com.babyraising.aipaperurine.ui.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.GrowthAdapter;
import com.babyraising.aipaperurine.adapter.MedalAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.GrowthPointBean;
import com.babyraising.aipaperurine.bean.MedalSimpleBean;
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

    private MedalAdapter signAdapter;
    private MedalAdapter levelAdapter;
    private MedalAdapter changeAdapter;

    private List<MedalSimpleBean> signList = new ArrayList<>();
    private List<MedalSimpleBean> levelList = new ArrayList<>();
    private List<MedalSimpleBean> changeList = new ArrayList<>();

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
                setRadius(DensityUtil.dip2px(60)).setCrop(true).build();
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

        updateMedalList();
    }

    private void updateMedalList() {
        int signDay = 0;
        if (!TextUtils.isEmpty(growthBean.getCONTINUITTY_SIGNIN_NUM())) {
            signDay = Integer.parseInt(growthBean.getCONTINUITTY_SIGNIN_NUM());
        }

        MedalSimpleBean signBean1 = new MedalSimpleBean();
        signBean1.setPIC(R.mipmap.icon_qiandao_yi_an);
        signBean1.setTAG("连续签到1天");
        MedalSimpleBean signBean2 = new MedalSimpleBean();
        signBean2.setPIC(R.mipmap.icon_qiandao_er_an);
        signBean2.setTAG("连续签到2天");
        MedalSimpleBean signBean3 = new MedalSimpleBean();
        signBean3.setPIC(R.mipmap.icon_qiandao_san_an);
        signBean3.setTAG("连续签到3天");
        MedalSimpleBean signBean4 = new MedalSimpleBean();
        signBean4.setPIC(R.mipmap.icon_qiandao_qi_an);
        signBean4.setTAG("连续签到7天");
        MedalSimpleBean signBean5 = new MedalSimpleBean();
        signBean5.setPIC(R.mipmap.icon_qiandao_shisi_an);
        signBean5.setTAG("连续签到14天");
        MedalSimpleBean signBean6 = new MedalSimpleBean();
        signBean6.setPIC(R.mipmap.icon_qiandao_sanshi_an);
        signBean6.setTAG("连续签到30天");
        MedalSimpleBean signBean7 = new MedalSimpleBean();
        signBean7.setPIC(R.mipmap.icon_qiandao_liushi_an);
        signBean7.setTAG("连续签到60天");

        if (signDay >= 1) {
            signBean1.setPIC(R.mipmap.icon_qiandao_yi);
        }

        if (signDay >= 2) {
            signBean2.setPIC(R.mipmap.icon_qiandao_er);
        }

        if (signDay >= 3) {
            signBean3.setPIC(R.mipmap.icon_qiandao_san);
        }

        if (signDay >= 7) {
            signBean4.setPIC(R.mipmap.icon_qiandao_qi);
        }

        if (signDay >= 14) {
            signBean5.setPIC(R.mipmap.icon_qiandao_shisi);
        }

        if (signDay >= 30) {
            signBean6.setPIC(R.mipmap.icon_qiandao_sanshi);
        }

        if (signDay >= 60) {
            signBean7.setPIC(R.mipmap.icon_qiandao_liushi);
        }

        signList.add(signBean1);
        signList.add(signBean2);
        signList.add(signBean3);
        signList.add(signBean4);
        signList.add(signBean5);
        signList.add(signBean6);
        signList.add(signBean7);
        signAdapter = new MedalAdapter(signList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        rv_sign_medal.setAdapter(signAdapter);
        rv_sign_medal.setLayoutManager(manager);

        int level = 0;
        if (!TextUtils.isEmpty(growthBean.getLEVEL())) {
            level = Integer.parseInt(growthBean.getLEVEL());
        }

        MedalSimpleBean levelBean1 = new MedalSimpleBean();
        levelBean1.setPIC(R.mipmap.icon_dengji_yi_an);
        levelBean1.setTAG("用户1级");
        MedalSimpleBean levelBean2 = new MedalSimpleBean();
        levelBean2.setPIC(R.mipmap.icon_dengji_shi_an);
        levelBean2.setTAG("用户10级");
        MedalSimpleBean levelBean3 = new MedalSimpleBean();
        levelBean3.setPIC(R.mipmap.icon_dengji_sanshi_an);
        levelBean3.setTAG("用户30级");
        MedalSimpleBean levelBean4 = new MedalSimpleBean();
        levelBean4.setPIC(R.mipmap.icon_dengji_wushi_an);
        levelBean4.setTAG("用户50级");
        MedalSimpleBean levelBean5 = new MedalSimpleBean();
        levelBean5.setPIC(R.mipmap.icon_dengji_yibai_an);
        levelBean5.setTAG("用户100级");

        if (level >= 1) {
            levelBean1.setPIC(R.mipmap.icon_dengji_yi);
        }

        if (level >= 10) {
            levelBean2.setPIC(R.mipmap.icon_dengji_shi);
        }

        if (level >= 30) {
            levelBean3.setPIC(R.mipmap.icon_dengji_sanshi);
        }

        if (level >= 50) {
            levelBean4.setPIC(R.mipmap.icon_dengji_wushi);
        }

        if (level >= 100) {
            levelBean5.setPIC(R.mipmap.icon_dengji_yibai);
        }

        levelList.add(levelBean1);
        levelList.add(levelBean2);
        levelList.add(levelBean3);
        levelList.add(levelBean4);
        levelList.add(levelBean5);
        levelAdapter = new MedalAdapter(levelList);
        GridLayoutManager manager1 = new GridLayoutManager(this, 3);
        rv_growth_medal.setAdapter(levelAdapter);
        rv_growth_medal.setLayoutManager(manager1);

        int count = 0;
        if (!TextUtils.isEmpty(growthBean.getDIAPER_ALL())) {
            count = Integer.parseInt(growthBean.getDIAPER_ALL());
        }

        MedalSimpleBean countBean1 = new MedalSimpleBean();
        countBean1.setPIC(R.mipmap.icon_genghuan_yipian_an);
        countBean1.setTAG("更换1片");
        MedalSimpleBean countBean2 = new MedalSimpleBean();
        countBean2.setPIC(R.mipmap.icon_genghuan_sanipian_an);
        countBean2.setTAG("更换3片");
        MedalSimpleBean countBean3 = new MedalSimpleBean();
        countBean3.setPIC(R.mipmap.icon_genghuan_shipian_an);
        countBean3.setTAG("更换10片");
        MedalSimpleBean countBean4 = new MedalSimpleBean();
        countBean4.setPIC(R.mipmap.icon_genghuan_wushipian_an);
        countBean4.setTAG("更换50片");
        MedalSimpleBean countBean5 = new MedalSimpleBean();
        countBean5.setPIC(R.mipmap.icon_genghuan_ybaipian_an);
        countBean5.setTAG("更换100片");
        MedalSimpleBean countBean6 = new MedalSimpleBean();
        countBean6.setPIC(R.mipmap.icon_genghuan_yiqianpian_an);
        countBean6.setTAG("更换1000片");


        if (count >= 1) {
            countBean1.setPIC(R.mipmap.icon_genghuan_yipian);
        }

        if (count >= 3) {
            countBean2.setPIC(R.mipmap.icon_genghuan_sanipian);
        }

        if (count >= 10) {
            countBean3.setPIC(R.mipmap.icon_genghuan_shipian);
        }

        if (count >= 50) {
            countBean4.setPIC(R.mipmap.icon_genghuan_wushipian);
        }

        if (count >= 100) {
            countBean5.setPIC(R.mipmap.icon_genghuan_ybaipian);
        }

        if (count >= 1000) {
            countBean6.setPIC(R.mipmap.icon_genghuan_yiqianpian);
        }

        changeList.add(countBean1);
        changeList.add(countBean2);
        changeList.add(countBean3);
        changeList.add(countBean4);
        changeList.add(countBean5);
        changeList.add(countBean6);
        changeAdapter = new MedalAdapter(changeList);
        GridLayoutManager manager2 = new GridLayoutManager(this, 3);
        rv_change_medal.setAdapter(changeAdapter);
        rv_change_medal.setLayoutManager(manager2);
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
