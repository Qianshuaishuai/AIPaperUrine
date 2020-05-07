package com.xinxin.aicare.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.OrderDetailAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.GoodBean;
import com.xinxin.aicare.bean.ShowMyYuyueBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.ShowMyYuyueResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_order_detail)
public class OrderDetailActivity extends BaseActivity {

    private ShowMyYuyueBean bean;
    private List<GoodBean> mList;
    private OrderDetailAdapter adapter;

    private String orderId;
    private UserBean userBean;

    @ViewInject(R.id.status_tip)
    private TextView statusTip;

    @ViewInject(R.id.iv_icon)
    private ImageView ivIcon;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.phone)
    private TextView phone;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @ViewInject(R.id.address)
    private TextView address;

    @ViewInject(R.id.express_num)
    private TextView expressNum;

    @ViewInject(R.id.order_num)
    private TextView orderNum;

    @ViewInject(R.id.create_time)
    private TextView createTime;

    @ViewInject(R.id.pay_time)
    private TextView payTime;

    @ViewInject(R.id.send_time)
    private TextView sendTime;

    @ViewInject(R.id.all_price)
    private TextView allPrice;

    @ViewInject(R.id.store_coupon)
    private TextView storeCoupon;

    @ViewInject(R.id.fare)
    private TextView fare;

    @ViewInject(R.id.order_all_price)
    private TextView orderAllPrice;

    @ViewInject(R.id.rv_order_detail)
    private RecyclerView rvOrderDetail;

    @ViewInject(R.id.real_pay_price)
    private TextView realPayPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        adapter = new OrderDetailAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvOrderDetail.setAdapter(adapter);
        rvOrderDetail.setLayoutManager(manager);
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        if (TextUtils.isEmpty(orderId)) {
            T.s("获取订单详情失败");
            finish();
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SHOWMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUE_ID", orderId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShowMyYuyueResponse response = gson.fromJson(result, ShowMyYuyueResponse.class);

                switch (response.getResult()) {
                    case 0:
                        bean = response.getData();
                        updateView();
                        break;
                    default:
                        T.s("获取订单详情失败");
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
        mList.clear();
        for (int a = 0; a < bean.getGOODS().size(); a++) {
            mList.add(bean.getGOODS().get(a));
        }
        adapter.notifyDataSetChanged();

        name.setText(bean.getCNAME());
        phone.setText(bean.getCPHONE());
        address.setText(bean.getADDRESS());
//        expressNum.setText(bean.get);
        orderNum.setText(bean.getPAYNO());
        createTime.setText(bean.getCREATETIME());
        payTime.setText(bean.getPAYTIME());
        allPrice.setText("￥" + bean.getTOTAL_PRICE());
        storeCoupon.setText("-￥" + bean.getCOUPON_DIS_COUNT());
        fare.setText("￥" + bean.getEXPRESSFEE());
        realPayPrice.setText("￥" + bean.getREALPAY());

//        switch (bean.get)
    }
}
