package com.babyraising.aipaperurine.ui.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.CouponAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.CouponBean;
import com.babyraising.aipaperurine.bean.CouponMoreBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CommonResponse;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.ui.message.MessageActivity;
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

@ContentView(R.layout.activity_coupon)
public class CouponActivity extends BaseActivity {

    private List<CouponMoreBean> couponList;
    private CouponAdapter couponAdapter;

    private UserBean bean;

    private String state = "1";

    private int mode = 0;

    @ViewInject(R.id.rv_coupon)
    private RecyclerView rvCoupon;

    @ViewInject(R.id.view_coupon_1)
    private View viewCoupon1;
    @ViewInject(R.id.view_coupon_2)
    private View viewCoupon2;
    @ViewInject(R.id.view_coupon_3)
    private View viewCoupon3;

    @ViewInject(R.id.tv_coupon_1)
    private TextView tvCoupon1;
    @ViewInject(R.id.tv_coupon_2)
    private TextView tvCoupon2;
    @ViewInject(R.id.tv_coupon_3)
    private TextView tvCoupon3;

    @ViewInject(R.id.tv_coupon_1_normal)
    private TextView tvCouponNormal1;
    @ViewInject(R.id.tv_coupon_2_normal)
    private TextView tvCouponNormal2;
    @ViewInject(R.id.tv_coupon_3_normal)
    private TextView tvCouponNormal3;

    @Event(R.id.layout_coupon_1)
    private void couponLayout1Click(View view) {
        if (state != "1") {
            viewCoupon1.setVisibility(View.VISIBLE);
            tvCoupon1.setVisibility(View.VISIBLE);
            tvCouponNormal1.setVisibility(View.GONE);

            viewCoupon2.setVisibility(View.GONE);
            tvCoupon2.setVisibility(View.GONE);
            tvCouponNormal2.setVisibility(View.VISIBLE);

            viewCoupon3.setVisibility(View.GONE);
            tvCoupon3.setVisibility(View.GONE);
            tvCouponNormal3.setVisibility(View.VISIBLE);

            state = "1";

            updateList();
        }
    }

    @Event(R.id.layout_coupon_2)
    private void couponLayout2Click(View view) {
        if (state != "2") {
            viewCoupon1.setVisibility(View.GONE);
            tvCoupon1.setVisibility(View.GONE);
            tvCouponNormal1.setVisibility(View.VISIBLE);

            viewCoupon2.setVisibility(View.VISIBLE);
            tvCoupon2.setVisibility(View.VISIBLE);
            tvCouponNormal2.setVisibility(View.GONE);

            viewCoupon3.setVisibility(View.GONE);
            tvCoupon3.setVisibility(View.GONE);
            tvCouponNormal3.setVisibility(View.VISIBLE);

            state = "2";
            updateList();
        }
    }

    @Event(R.id.layout_coupon_3)
    private void couponLayout3Click(View view) {
        if (state != "3") {
            viewCoupon1.setVisibility(View.GONE);
            tvCoupon1.setVisibility(View.GONE);
            tvCouponNormal1.setVisibility(View.VISIBLE);

            viewCoupon2.setVisibility(View.GONE);
            tvCoupon2.setVisibility(View.GONE);
            tvCouponNormal2.setVisibility(View.VISIBLE);

            viewCoupon3.setVisibility(View.VISIBLE);
            tvCoupon3.setVisibility(View.VISIBLE);
            tvCouponNormal3.setVisibility(View.GONE);

            state = "3";
            updateList();
        }
    }

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
        updateList();

        Intent intent = getIntent();
        mode = intent.getIntExtra("coupon-mode", 0);

    }

    private void initView() {
        couponList = new ArrayList<>();
        couponAdapter = new CouponAdapter(couponList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvCoupon.setAdapter(couponAdapter);
        rvCoupon.setLayoutManager(manager);
        couponAdapter.setOnItemClickListener(new CouponAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mode == 101) {
                    Intent data = new Intent();
                    data.putExtra("couponId", couponList.get(position).getCOUPON_ID());
                    setResult(10000, data);
                    finish();
                }
            }
        });
    }

    private void updateList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_COUPONLIST);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("STATE", state);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CouponResponse response = gson.fromJson(result, CouponResponse.class);
                switch (response.getResult()) {
                    case 0:
                        couponList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            couponList.add(response.getData().get(m));
                        }
                        couponAdapter.notifyDataSetChanged();
                        break;
                    default:
                        T.s("获取优惠券列表失败");
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
}
