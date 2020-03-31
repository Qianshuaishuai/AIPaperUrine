package com.babyraising.aipaperurine.ui.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.OrderAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.bean.YuYueBean;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.ListMyYuyueResponse;
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

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseActivity {

    private int orderType = 1;
    private String[] orderStatus = {"0", "1", "3", "4", "5"};
    private List<YuYueBean> orderList;
    private OrderAdapter adapter;
    private UserBean bean;

    @ViewInject(R.id.view_order_1)
    private View viewOrder1;

    @ViewInject(R.id.rv_order)
    private RecyclerView rvOrder;
    @ViewInject(R.id.view_order_2)
    private View viewOrder2;
    @ViewInject(R.id.view_order_3)
    private View viewOrder3;
    @ViewInject(R.id.view_order_4)
    private View viewOrder4;
    @ViewInject(R.id.view_order_5)
    private View viewOrder5;

    @ViewInject(R.id.tv_order_1)
    private TextView tvOrder1;
    @ViewInject(R.id.tv_order_2)
    private TextView tvOrder2;
    @ViewInject(R.id.tv_order_3)
    private TextView tvOrder3;
    @ViewInject(R.id.tv_order_4)
    private TextView tvOrder4;
    @ViewInject(R.id.tv_order_5)
    private TextView tvOrder5;

    @ViewInject(R.id.tv_order_1_normal)
    private TextView tvOrderNormal1;
    @ViewInject(R.id.tv_order_2_normal)
    private TextView tvOrderNormal2;
    @ViewInject(R.id.tv_order_3_normal)
    private TextView tvOrderNormal3;
    @ViewInject(R.id.tv_order_4_normal)
    private TextView tvOrderNormal4;
    @ViewInject(R.id.tv_order_5_normal)
    private TextView tvOrderNormal5;

    @Event(R.id.layout_order_1)
    private void orderLayout1Click(View view) {
        if (orderType != 1) {
            viewOrder1.setVisibility(View.VISIBLE);
            tvOrder1.setVisibility(View.VISIBLE);
            tvOrderNormal1.setVisibility(View.GONE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 1;
        }
    }

    @Event(R.id.layout_order_2)
    private void orderLayout2Click(View view) {
        if (orderType != 2) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.VISIBLE);
            tvOrder2.setVisibility(View.VISIBLE);
            tvOrderNormal2.setVisibility(View.GONE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 2;
        }
    }

    @Event(R.id.layout_order_3)
    private void orderLayout3Click(View view) {
        if (orderType != 3) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.VISIBLE);
            tvOrder3.setVisibility(View.VISIBLE);
            tvOrderNormal3.setVisibility(View.GONE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 3;
        }
    }

    @Event(R.id.layout_order_4)
    private void orderLayout4Click(View view) {
        if (orderType != 4) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.VISIBLE);
            tvOrder4.setVisibility(View.VISIBLE);
            tvOrderNormal4.setVisibility(View.GONE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 4;
        }
    }

    @Event(R.id.layout_order_5)
    private void orderLayout5Click(View view) {
        if (orderType != 5) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.VISIBLE);
            tvOrder5.setVisibility(View.VISIBLE);
            tvOrderNormal5.setVisibility(View.GONE);

            orderType = 5;
        }
    }

    @Event(R.id.layout_back)
    private void back(View view) {
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
    }

    private void updateList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_COUPONLIST);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("STATE", "0");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ListMyYuyueResponse response = gson.fromJson(result, ListMyYuyueResponse.class);
                switch (response.getResult()) {
                    case 0:
                        orderList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            orderList.add(response.getData().get(m));
                        }
                        adapter.notifyDataSetChanged();
                        System.out.println(result);
                        break;
                    default:
                        T.s("获取订单列表失败");
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
        orderList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(orderList);
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(manager);

    }
}
