package com.babyraising.aipaperurine.ui.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.PreYuyueBean;
import com.babyraising.aipaperurine.bean.SureOrderBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.GoodsInfoResponse;
import com.babyraising.aipaperurine.response.PreYuyueCouponResponse;
import com.babyraising.aipaperurine.response.PreYuyueResponse;
import com.babyraising.aipaperurine.response.SubmitYuyueOrderResponse;
import com.babyraising.aipaperurine.ui.pay.PayActivity;
import com.babyraising.aipaperurine.ui.person.CouponActivity;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;

@ContentView(R.layout.activity_sure_order)
public class SureOrderActivity extends BaseActivity {

    private PersonBean personBean;
    private UserBean userBean;
    private SureOrderBean sureOrderBean;
    private String couponId = "";
    private PreYuyueBean preYuyueBean;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @ViewInject(R.id.username)
    private TextView userName;
//
//    @ViewInject(R.id.address)
//    private TextView address;

    @ViewInject(R.id.phone)
    private TextView phone;

    @ViewInject(R.id.detail)
    private TextView detail;

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.goodname)
    private TextView goodname;

    @ViewInject(R.id.good_detail)
    private TextView good_detail;

    @ViewInject(R.id.good_count)
    private TextView good_count;

    @ViewInject(R.id.price)
    private TextView price;

    @ViewInject(R.id.fare)
    private TextView fare;

    @ViewInject(R.id.coupon_count_layout)
    private LinearLayout couponCountLayout;

    @ViewInject(R.id.coupon_price)
    private TextView couponPrice;

    @ViewInject(R.id.service)
    private TextView service;

    @ViewInject(R.id.dtime)
    private TextView dtime;

    @ViewInject(R.id.dtype)
    private TextView dtype;

    @ViewInject(R.id.note)
    private EditText note;

    @Event(R.id.coupon_layout)
    private void couponLayoutClick(View view) {
        Intent intent = new Intent(this, CouponActivity.class);
        intent.putExtra("coupon-mode", 101);
        startActivityForResult(intent, 10001);
    }

    @ViewInject(R.id.all_price)
    private TextView allPrice;

    @Event(R.id.commit)
    private void commit(View view) {
        submitOrder(couponId);
    }

    @ViewInject(R.id.coupon_detail_layout)
    private RelativeLayout couponDetailLayout;

    @ViewInject(R.id.rv_coupon)
    private RecyclerView rvCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        personBean = ((PaperUrineApplication) getApplication()).getPersonInfo();

        Intent intent = getIntent();
        Gson gson = new Gson();
        sureOrderBean = gson.fromJson(intent.getStringExtra("sureOrderBean"), SureOrderBean.class);
        if (TextUtils.isEmpty(sureOrderBean.getGoodsInfoBean().getGOODS_ID())) {
            T.s("获取订单失败");
            finish();
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PREYUYUE);
        params.addQueryStringParameter("GOODS_ID", sureOrderBean.getGoodsInfoBean().getGOODS_ID());
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("NUMBER", sureOrderBean.getGoodCount());
        params.addQueryStringParameter("BRAND_SIZE", sureOrderBean.getGoodsInfoBean().getBRAND_SIZE());
        params.addQueryStringParameter("ATTRIBUTE_VALUE1", sureOrderBean.getSelectValue1());
        params.addQueryStringParameter("ATTRIBUTE_VALUE2", sureOrderBean.getSelectValue2());
        params.addQueryStringParameter("ATTRIBUTE_VALUE3", sureOrderBean.getSelectValue3());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PreYuyueResponse response = gson.fromJson(result, PreYuyueResponse.class);

                switch (response.getResult()) {
                    case 0:
                        preYuyueBean = response.getData();
                        updatePreView(preYuyueBean);
                        break;
                    default:
                        System.out.println("失败:" + result);
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

    private void updatePreView(PreYuyueBean bean) {
        goodname.setText(bean.getCARDINFO().getTITLE());
        good_count.setText("x" + bean.getTOTAL_NUM());
        fare.setText("¥" + bean.getEXPRESSFEE());
        good_detail.setText("参数:" + bean.getCARDINFO().getBRAND_SIZE() + "  " + bean.getCARDINFO().getATTRIBUTE_VALUE1() + "  " + bean.getCARDINFO().getATTRIBUTE_VALUE2() + "  " + bean.getCARDINFO().getATTRIBUTE_VALUE3());
        x.image().bind(icon, bean.getCARDINFO().getPIC());
        service.setText(bean.getSERVICE());

        detail.setText(bean.getADDRESS().getADDRESS());
        phone.setText(bean.getADDRESS().getCPHONE());
        userName.setText(bean.getADDRESS().getCNAME());
//
//
//        System.out.println(bean.getADDRESS().getADDRESS());
//        System.out.println(bean.getADDRESS().getCNAME());
//        System.out.println(bean.getADDRESS().getCPHONE());

        price.setText("¥" + bean.getTOTAL_PRICE());
        allPrice.setText("¥" + bean.getREAL_PAY());
    }

    private void useCoupon(String couponId) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PREYUYUECOUPON);
        params.addQueryStringParameter("GOODS_ID", sureOrderBean.getGoodsInfoBean().getGOODS_ID());
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("NUMBER", sureOrderBean.getGoodCount());
        params.addQueryStringParameter("BRAND_SIZE", sureOrderBean.getGoodsInfoBean().getBRAND_SIZE());
        params.addQueryStringParameter("ATTRIBUTE_VALUE1", sureOrderBean.getSelectValue1());
        params.addQueryStringParameter("ATTRIBUTE_VALUE2", sureOrderBean.getSelectValue2());
        params.addQueryStringParameter("ATTRIBUTE_VALUE3", sureOrderBean.getSelectValue3());
        params.addQueryStringParameter("COUPON_ID", couponId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PreYuyueCouponResponse response = gson.fromJson(result, PreYuyueCouponResponse.class);
                switch (response.getResult()) {
                    case 0:
                        allPrice.setText("¥" + response.getData().getREAL_PAY());
                        couponCountLayout.setVisibility(View.VISIBLE);
                        if(TextUtils.isEmpty(response.getData().getCOUPON_DISCOUNT())){
                            couponPrice.setText("-¥" + "0.00");
                        }else{
                            couponPrice.setText("-¥" + response.getData().getCOUPON_DISCOUNT());
                        }
                        break;
                    default:
                        System.out.println("失败:" + result);
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

    private void submitOrder(String couponId) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SUBMITYUYUEORDER);
        params.addQueryStringParameter("GOODS_ID", sureOrderBean.getGoodsInfoBean().getGOODS_ID());
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("NUMBER", sureOrderBean.getGoodCount());
        params.addQueryStringParameter("BRAND_SIZE", sureOrderBean.getGoodsInfoBean().getBRAND_SIZE());
        params.addQueryStringParameter("ATTRIBUTE_VALUE1", sureOrderBean.getSelectValue1());
        params.addQueryStringParameter("ATTRIBUTE_VALUE2", sureOrderBean.getSelectValue2());
        params.addQueryStringParameter("ATTRIBUTE_VALUE3", sureOrderBean.getSelectValue3());
        params.addQueryStringParameter("ADDRESS_ID",preYuyueBean.getADDRESS().getADDRESS_ID() );
        params.addQueryStringParameter("COUPON_ID", couponId);
        params.addQueryStringParameter("BUYERMSG", note.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                SubmitYuyueOrderResponse response = gson.fromJson(result, SubmitYuyueOrderResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        startPayActivity(response.getData().getYUYUE_ID(), response.getData().getREAL_PAY());
                        finish();
                        break;
                    default:
                        T.s("下单失败");
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

    private void startPayActivity(String yuyueId, String price) {
        Intent intent = new Intent(this, PayActivity.class);
        intent.putExtra("yuyueId", yuyueId);
        intent.putExtra("realPay", price);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 10000) {
            couponId = data.getStringExtra("couponId");
            useCoupon(couponId);
        }
    }
}
