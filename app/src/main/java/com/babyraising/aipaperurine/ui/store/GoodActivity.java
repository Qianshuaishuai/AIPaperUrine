package com.babyraising.aipaperurine.ui.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.TagAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.GoodBean;
import com.babyraising.aipaperurine.bean.GoodsInfoBean;
import com.babyraising.aipaperurine.bean.SureOrderBean;
import com.babyraising.aipaperurine.response.GoodsInfoResponse;
import com.babyraising.aipaperurine.response.HomeCarouselResponse;
import com.babyraising.aipaperurine.util.BannerImageLoader;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_good)
public class GoodActivity extends BaseActivity {

    private GoodsInfoBean goodBean;
    private SureOrderBean sureOrderBean;

    @ViewInject(R.id.buy)
    private Button buyBt;

    @ViewInject(R.id.param_layout)
    private RelativeLayout paramLayout;

    @ViewInject(R.id.buy_layout)
    private RelativeLayout buyLayout;

    @ViewInject(R.id.price)
    private TextView price;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.subtitle)
    private TextView subtitle;

    @ViewInject(R.id.address)
    private TextView address;

    @ViewInject(R.id.buy_count)
    private TextView buyCount;

    @ViewInject(R.id.service)
    private TextView service;

    @ViewInject(R.id.active)
    private TextView active;

    @ViewInject(R.id.brand)
    private TextView brand;

    @ViewInject(R.id.name1)
    private TextView name1;

    @ViewInject(R.id.name2)
    private TextView name2;

    @ViewInject(R.id.name3)
    private TextView name3;

    @ViewInject(R.id.layout1)
    private TextView layout1;

    @ViewInject(R.id.layout2)
    private TextView layout2;

    @ViewInject(R.id.layout3)
    private TextView layout3;

    @ViewInject(R.id.value1)
    private TextView value1;

    @ViewInject(R.id.value2)
    private TextView value2;

    @ViewInject(R.id.value3)
    private TextView value3;

    @ViewInject(R.id.color)
    private TextView color;

    @ViewInject(R.id.tv_tag1)
    private TextView tvTag1;

    @ViewInject(R.id.rv_tag1)
    private RecyclerView rvTag1;

    @ViewInject(R.id.tv_tag2)
    private TextView tvTag2;

    @ViewInject(R.id.rv_tag2)
    private RecyclerView rvTag2;

    @ViewInject(R.id.tv_tag3)
    private TextView tvTag3;

    @ViewInject(R.id.rv_tag3)
    private RecyclerView rvTag3;

    @ViewInject(R.id.simple_icon)
    private ImageView simpleIcon;

    @ViewInject(R.id.simple_name)
    private TextView simpleName;

    @ViewInject(R.id.simple_subtitle)
    private TextView simpleSubtitle;

    @ViewInject(R.id.simple_price)
    private TextView simplePrice;

    @ViewInject(R.id.simple_count)
    private TextView simpleCount;

    @ViewInject(R.id.iv_icon)
    private Banner banner;

    private String selectValue1 = "";
    private String selectValue2 = "";
    private String selectValue3 = "";

    @Event(R.id.sure)
    private void sure(View view) {
        buyBt.setVisibility(View.VISIBLE);
        paramLayout.setVisibility(View.GONE);
    }

    @Event(R.id.buy)
    private void buy(View view) {
        buyBt.setVisibility(View.GONE);
        buyLayout.setVisibility(View.VISIBLE);
    }

    @Event(R.id.param_simple_layout)
    private void showParamLayout(View view) {
        buyBt.setVisibility(View.GONE);
        paramLayout.setVisibility(View.VISIBLE);
    }

    @Event(R.id.sure_buy)
    private void sureBuy(View view) {
        buyBt.setVisibility(View.VISIBLE);
        buyLayout.setVisibility(View.GONE);

        sureBuy();
    }

    private void sureBuy() {
        if(simpleCount.getText().toString().equals("0")){
            T.s("商品数不能为0");
            return;
        }
        sureOrderBean.setGoodCount(simpleCount.getText().toString());
        sureOrderBean.setSelectValue1(selectValue1);
        sureOrderBean.setSelectValue2(selectValue2);
        sureOrderBean.setSelectValue3(selectValue3);

        Gson gson = new Gson();
        Intent intent = new Intent(this, SureOrderActivity.class);
        intent.putExtra("sureOrderBean", gson.toJson(sureOrderBean));
        startActivity(intent);
        finish();
    }

    @Event(R.id.iv_reduce)
    private void reduce(View view) {
        int count = Integer.parseInt(simpleCount.getText().toString());
        if (count > 0) {
            count = count - 1;
            simpleCount.setText("" + count);
        }
    }

    @Event(R.id.iv_add)
    private void add(View view) {
        int count = Integer.parseInt(simpleCount.getText().toString());
        count = count + 1;
        simpleCount.setText("" + count);
    }

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    private List<String> bannerImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String goodId = intent.getStringExtra("goodsId");

        if (TextUtils.isEmpty(goodId)) {
            T.s("获取商品详情失败");
            finish();
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GOODSINFO);
        params.addQueryStringParameter("GOODS_ID", goodId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GoodsInfoResponse response = gson.fromJson(result, GoodsInfoResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        goodBean = response.getData();
                        sureOrderBean.setGoodsInfoBean(goodBean);
                        updateView(response.getData());
                        break;
                    default:
                        System.out.println("获取失败:" + result);
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

    private void updateView(GoodsInfoBean bean) {
        name.setText(bean.getTITLE());
        subtitle.setText(bean.getINFO());
        price.setText("￥" + bean.getSALEPRICE());
        buyCount.setText("已有" + bean.getSALENUM() + "人购买");
        address.setText(bean.getSENDADDRESS());
        active.setText(bean.getHDTIP());
        brand.setText(bean.getBRAND_NAME());
        service.setText(bean.getSERVICE());

        simpleName.setText(bean.getTITLE());
        simpleSubtitle.setText(bean.getINFO());
        simplePrice.setText("￥" + bean.getSALEPRICE());


        String[] images = bean.getPICS().split(",");
        for (int a = 0; a < images.length; a++) {
            bannerImages.add(images[a]);
        }
        //设置图片加载器
        banner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        banner.setImages(bannerImages);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        if (images.length > 0) {
            x.image().bind(simpleIcon, images[0]);
        }

        if (TextUtils.isEmpty(bean.getATTRIBUTE_NAME1())) {
            layout1.setVisibility(View.GONE);
            tvTag1.setVisibility(View.GONE);
            rvTag1.setVisibility(View.GONE);
        } else {
            name1.setText(bean.getATTRIBUTE_NAME1());
            value1.setText(bean.getATTRIBUTE_VALUE1());

            final String[] tag1Value = bean.getATTRIBUTE_VALUE1().split(",");
            List<String> tag1List = new ArrayList<>();
            for (int s = 0; s < tag1Value.length; s++) {
                tag1List.add(tag1Value[s]);
            }

            final TagAdapter adapter1 = new TagAdapter(tag1List);
            GridLayoutManager manager1 = new GridLayoutManager(this, 5);
            rvTag1.setLayoutManager(manager1);
            rvTag1.setAdapter(adapter1);
            adapter1.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    adapter1.setCurrentPosition(position);
                    adapter1.notifyDataSetChanged();
                    selectValue1 = tag1Value[position];
                }
            });
            tvTag1.setText(bean.getATTRIBUTE_NAME1());
            selectValue1 = tag1Value[0];
        }

        if (TextUtils.isEmpty(bean.getATTRIBUTE_NAME2())) {
            layout2.setVisibility(View.GONE);
            tvTag2.setVisibility(View.GONE);
            rvTag2.setVisibility(View.GONE);
        } else {
            name2.setText(bean.getATTRIBUTE_NAME2());
            value2.setText(bean.getATTRIBUTE_VALUE2());

            final String[] tag2Value = bean.getATTRIBUTE_VALUE2().split(",");
            List<String> tag2List = new ArrayList<>();
            for (int s = 0; s < tag2Value.length; s++) {
                tag2List.add(tag2Value[s]);
            }

            final TagAdapter adapter2 = new TagAdapter(tag2List);
            GridLayoutManager manager2 = new GridLayoutManager(this, 5);
            rvTag2.setLayoutManager(manager2);
            rvTag2.setAdapter(adapter2);
            adapter2.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    adapter2.setCurrentPosition(position);
                    adapter2.notifyDataSetChanged();
                    selectValue2 = tag2Value[position];
                }
            });
            tvTag2.setText(bean.getATTRIBUTE_NAME2());
            selectValue2 = tag2Value[0];
        }

        if (TextUtils.isEmpty(bean.getATTRIBUTE_NAME3())) {
            layout3.setVisibility(View.GONE);
            tvTag3.setVisibility(View.GONE);
            rvTag3.setVisibility(View.GONE);
        } else {
            name3.setText(bean.getATTRIBUTE_NAME3());
            value3.setText(bean.getATTRIBUTE_VALUE3());

            final String[] tag3Value = bean.getATTRIBUTE_VALUE3().split(",");
            List<String> tag3List = new ArrayList<>();
            for (int s = 0; s < tag3Value.length; s++) {
                tag3List.add(tag3Value[s]);
            }

            final TagAdapter adapter3 = new TagAdapter(tag3List);
            GridLayoutManager manager3 = new GridLayoutManager(this, 5);
            rvTag3.setLayoutManager(manager3);
            rvTag3.setAdapter(adapter3);
            adapter3.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    adapter3.setCurrentPosition(position);
                    adapter3.notifyDataSetChanged();
                    selectValue3 = tag3Value[position];
                }
            });
            tvTag3.setText(bean.getATTRIBUTE_NAME3());
            selectValue3 = tag3Value[0];
        }
    }

    private void initView() {
        sureOrderBean = new SureOrderBean();
    }
}
