package com.babyraising.aipaperurine.ui.info;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.BrandAdapter;
import com.babyraising.aipaperurine.adapter.BrandSizeAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.BrandSizeBean;
import com.babyraising.aipaperurine.bean.BrandSizeSimpleBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.BrandSizeResponse;
import com.babyraising.aipaperurine.response.MessageResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_pick_size)
public class PickSizeActivity extends BaseActivity {

    private UserBean userBean;
    private List<BrandSizeBean> allList;
    private String memberId;

    private List<BrandSizeSimpleBean> sizeList;
    private BrandSizeAdapter adapter;

    private List<String> brandList;
    private BrandAdapter brandAdapter;
    private String currentBrandId;
    private String currentSize;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @ViewInject(R.id.group_brand)
    private LinearLayout brandGroup;

    @ViewInject(R.id.brand_name)
    private TextView brandName;

    @ViewInject(R.id.iv_brand_more)
    private ImageView ivBrandMore;

    @ViewInject(R.id.rv_size)
    private RecyclerView rvSize;

    @ViewInject(R.id.rv_brand)
    private RecyclerView rvBrand;

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

    @Event(R.id.sure)
    private void sure(View view) {
        editMemberSize();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void editMemberSize() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GETBRANDSIZE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberId);
        params.addQueryStringParameter("DIAPER_BRAND", currentBrandId);
        params.addQueryStringParameter("DIAPER_SIZE", currentSize);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                BrandSizeResponse response = gson.fromJson(result, BrandSizeResponse.class);
                switch (response.getResult()) {
                    case 0:
                        finish();
                        T.s("修改尺码成功");
                        break;

                    default:
                        T.s("修改尺码失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GETBRANDSIZE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                BrandSizeResponse response = gson.fromJson(result, BrandSizeResponse.class);
                switch (response.getResult()) {
                    case 0:
                        allList = response.getData();
                        updateView(allList);
                        break;

                    default:
                        T.s("获取品牌尺码失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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

    private void updateView(List<BrandSizeBean> beanList) {
        if (beanList.size() > 0) {
            brandName.setText(beanList.get(0).getBRAND_NAME());
            currentBrandId = beanList.get(0).getBRAND_ID();

            String[] brandSizes = beanList.get(0).getBRAND_SIZES().split(",");
            String[] brandDescs = beanList.get(0).getBRAND_SIZE_DESCS().split(",");

            sizeList.clear();
            currentSize = brandSizes[0];
            for (int b = 0; b < brandSizes.length; b++) {
                BrandSizeSimpleBean bean = new BrandSizeSimpleBean();
                bean.setBRAND_SIZE(brandSizes[b]);
                bean.setBRAND_SIZE_DESC(brandDescs[b]);
                sizeList.add(bean);
            }

            adapter.notifyDataSetChanged();
        }

        brandList.clear();
        for (int a = 0; a < allList.size(); a++) {
            brandList.add(allList.get(a).getBRAND_NAME());
        }

        brandAdapter.notifyDataSetChanged();
    }

    private void updateData(int position) {
        if (allList.size() > 0) {
            brandName.setText(allList.get(position).getBRAND_NAME());
            currentBrandId = allList.get(position).getBRAND_ID();
            String[] brandSizes = allList.get(position).getBRAND_SIZES().split(",");
            String[] brandDescs = allList.get(position).getBRAND_SIZE_DESCS().split(",");

            sizeList.clear();
            currentSize = brandSizes[0];
            for (int b = 0; b < brandSizes.length; b++) {
                BrandSizeSimpleBean bean = new BrandSizeSimpleBean();
                bean.setBRAND_SIZE(brandSizes[b]);
                bean.setBRAND_SIZE_DESC(brandDescs[b]);
                sizeList.add(bean);
            }

            adapter.notifyDataSetChanged();
        }
    }


    private void initView() {
        allList = new ArrayList<>();
        sizeList = new ArrayList<>();
        adapter = new BrandSizeAdapter(sizeList);
        adapter.setOnItemClickListener(new BrandSizeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
//                adapter.setCurrentPosition(position);
//                adapter.notifyDataSetChanged();
//                Intent data = new Intent();
//                data.putExtra("brandSize", sizeList.get(position).getBRAND_SIZE());
//                data.putExtra("brand", brandName.getText().toString());
//                data.putExtra("brandId", currentBrandId);
//                setResult(10000, data);
//                finish();
                currentSize = sizeList.get(position).getBRAND_SIZE();
                adapter.setCurrentPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSize.setAdapter(adapter);
        rvSize.setLayoutManager(manager);

        brandList = new ArrayList<>();
        brandAdapter = new BrandAdapter(brandList, this);
        brandAdapter.setOnItemClickListener(new BrandAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        rvBrand.setAdapter(brandAdapter);
        rvBrand.setLayoutManager(manager1);
    }

    public void changeBrand(int position) {
        brandGroup.setVisibility(View.GONE);
        ivBrandMore.setImageResource(R.mipmap.iconmonstr_right_chakanxiangqing);
        updateData(position);
        brandAdapter.notifyDataSetChanged();
    }
}
