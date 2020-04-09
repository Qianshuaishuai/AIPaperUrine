package com.babyraising.aipaperurine.ui.order;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.OrderAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.bean.YuYueBean;
import com.babyraising.aipaperurine.response.CommonResponse;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.ListMyYuyueResponse;
import com.babyraising.aipaperurine.response.ShowSimpleMyYuyueResponse;
import com.babyraising.aipaperurine.ui.pay.PayActivity;
import com.babyraising.aipaperurine.ui.person.RefundDetailActivity;
import com.babyraising.aipaperurine.ui.person.RefundDetailBActivity;
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

    private int orderType = 0;
    private String[] orderStatus = {"0", "1", "3", "4", "5"};
    private List<YuYueBean> orderList;
    private OrderAdapter adapter;
    private UserBean bean;
    private String cancelId;

    private AlertDialog cancelTipDialog;

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
        if (orderType != 0) {
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

            orderType = 0;
            updateList();
        }
    }

    @Event(R.id.layout_order_2)
    private void orderLayout2Click(View view) {
        if (orderType != 1) {
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

            orderType = 1;
            updateList();
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
            updateList();
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
            updateList();
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
            updateList();
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
        initCancelDialog();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getApplication()).getUserInfo();
        updateList();
    }

    private void updateList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_LISTMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("STATE", orderType);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ListMyYuyueResponse response = gson.fromJson(result, ListMyYuyueResponse.class);

                switch (response.getResult()) {
                    case 0:
                        System.out.println(result);
                        orderList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            orderList.add(response.getData().get(m));
                        }
                        adapter.notifyDataSetChanged();
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
        adapter = new OrderAdapter(orderList, this);
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(manager);

    }

    public void orderDetail(String orderId) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        startActivity(intent);
    }

    public void goToRefundActivity(String refundId) {
        Intent intent = new Intent(this, RefundActivity.class);
        intent.putExtra("yuyueId", refundId);
        startActivity(intent);
    }

    public void goToRefundAActivity(String refundId) {
        Intent intent = new Intent(this, RefundDetailActivity.class);
        intent.putExtra("refundId", refundId);
        startActivity(intent);
    }

    public void goToRefundBActivity(String refundId) {
        Intent intent = new Intent(this, RefundDetailBActivity.class);
        intent.putExtra("refundId", refundId);
        startActivity(intent);
    }

    public void seeToExpress(String yuyueId) {
        Intent intent = new Intent(this, ExpressDetailActivity.class);
        intent.putExtra("yuyueId", yuyueId);
        startActivity(intent);
    }

    public void cancelOrder(String yuyueId) {
        cancelTipDialog.show();
        cancelId = yuyueId;
    }

    public void sureOrder(String yuyueId){
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_RECEIVEMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("YUYUE_ID", yuyueId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);

                switch (response.getResult()) {
                    case 0:
                        T.s("确认收货成功");
                        updateList();
                        break;
                    default:
                        T.s("确认收货失败");
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

    private void initCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        cancelTipDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);

        titleTextView.setText("确定取消订单?");

        cancelButton.setText("取消");

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                cancelTipDialog.cancel();
            }
        });

        sureButton.setText("确定");

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                cancelTipDialog.cancel();
                cancelOrderPost();
            }
        });

        cancelTipDialog.setCancelable(false);
//        tipDialog.show();
    }

    private void goToPay(String yuyueId) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SHOWSIMPLEMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("YUYUE_ID", yuyueId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShowSimpleMyYuyueResponse response = gson.fromJson(result, ShowSimpleMyYuyueResponse.class);

                switch (response.getResult()) {
                    case 0:
                        Intent intent = new Intent(OrderActivity.this, PayActivity.class);
                        intent.putExtra("yuyueId", response.getData().getYUYUE_ID());
                        intent.putExtra("realPay", response.getData().getREAL_PAY());
                        startActivity(intent);
                        break;
                    default:
                        T.s("获取订单简易信息失败");
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

    private void cancelOrderPost() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_CANCELMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("YUYUE_ID", cancelId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);

                switch (response.getResult()) {
                    case 0:
                        T.s("取消订单成功");
                        updateList();
                        break;
                    default:
                        T.s("取消订单失败");
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
