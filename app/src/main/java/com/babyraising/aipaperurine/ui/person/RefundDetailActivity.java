package com.babyraising.aipaperurine.ui.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.ShowMyYuyueRefundBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CommonResponse;
import com.babyraising.aipaperurine.response.ShowMyYuyueRefundResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_refund_detail)
public class RefundDetailActivity extends BaseActivity {

    @ViewInject(R.id.status_tip)
    private TextView statusTip;

    @ViewInject(R.id.time_tip)
    private TextView timeTip;

    @ViewInject(R.id.iv_icon)
    private ImageView ivIcon;

    @ViewInject(R.id.refund_all_price)
    private TextView refundAllPrice;

    @ViewInject(R.id.good_name)
    private TextView goodName;

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.good_params)
    private TextView goodParams;

    @ViewInject(R.id.refund_reason)
    private TextView refundReason;

    @ViewInject(R.id.refund_price)
    private TextView refundPrice;

    @ViewInject(R.id.refund_time)
    private TextView refundTime;

    @ViewInject(R.id.refund_number)
    private TextView refundNumber;

    @ViewInject(R.id.reason_detail)
    private EditText reasonDetail;

    @ViewInject(R.id.reason_fail)
    private TextView reasonFail;

    @ViewInject(R.id.layout_refund_fail)
    private LinearLayout layoutRefundFail;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.phone)
    private TextView phone;

    @ViewInject(R.id.address)
    private TextView address;

    @ViewInject(R.id.express_layout)
    private LinearLayout expressLayout;

    @ViewInject(R.id.express)
    private EditText express;

    @Event(R.id.express_sure)
    private void expressSure(View view) {
        submitTno(express.getText().toString());
    }

    private UserBean userBean;
    private ShowMyYuyueRefundBean bean;
    private String refundId;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        refundId = intent.getStringExtra("refundId");
        System.out.println(refundId);
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SHOWMYYUYUEREFUND);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUECARD_ID", refundId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShowMyYuyueRefundResponse response = gson.fromJson(result, ShowMyYuyueRefundResponse.class);

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

    private void submitTno(String TNO) {
        if (TextUtils.isEmpty(TNO)) {
            T.s("单号不能为空");
            return;
        }
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SUBMITREFUNDNO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUECARD_ID", refundId);
        params.addQueryStringParameter("TNO", TNO);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);

                switch (response.getResult()) {
                    case 0:
                        T.s("回填成功");
                        expressLayout.setVisibility(View.GONE);
                        break;
                    default:
                        T.s("回填失败");
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
        switch (bean.getSTATE()) {
            case "6":
                statusTip.setText("退款中");
                ivIcon.setImageResource(R.mipmap.icon_tuikuanzhong);

                if (bean.getCARD_STATE().equals("4") || bean.getCARD_STATE().equals("5")) {
                    if (TextUtils.isEmpty(bean.getTNO())) {
                        expressLayout.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case "7":
                statusTip.setText("退款成功");
                ivIcon.setImageResource(R.mipmap.icon_tuikuanchenggong);
                break;
            case "8":
                statusTip.setText("退款失败");
                ivIcon.setImageResource(R.mipmap.icon_tuikuanzhong);
                break;
            case "9":
                statusTip.setText("退款申请中，等待商家审核");
                ivIcon.setImageResource(R.mipmap.icon_tuikuanshenhe);
                break;
        }

        x.image().bind(icon,bean.getPICS());
        timeTip.setText(bean.getAPPLYREFUNDTIME());
//        refundAllPrice.setText(bean.getREFUNDAMT());
        goodName.setText(bean.getTITLE());
        String params = "";
        if (!TextUtils.isEmpty(bean.getBRAND_SIZE())) {
            params = params + bean.getBRAND_SIZE();
        }

        if (!TextUtils.isEmpty(bean.getATTRIBUTE_VALUE1())) {
            params = params + bean.getATTRIBUTE_VALUE1();
        }


        if (!TextUtils.isEmpty(bean.getATTRIBUTE_VALUE2())) {
            params = params + bean.getATTRIBUTE_VALUE2();
        }


        if (!TextUtils.isEmpty(bean.getATTRIBUTE_VALUE3())) {
            params = params + bean.getATTRIBUTE_VALUE3();
        }

        goodParams.setText(params + "x" + bean.getNUM());
        refundReason.setText(bean.getREFUND_INFO());
        refundPrice.setText(bean.getREFUNDAMT());
        refundTime.setText(bean.getREFUNDTIME());
        refundNumber.setText(bean.getREFUNDNO());
        name.setText(bean.getANAME());
        phone.setText(bean.getAPHONE());
        address.setText(bean.getAADRESS());
        if (TextUtils.isEmpty(bean.getMSG())) {
            layoutRefundFail.setVisibility(View.GONE);
        } else {
            layoutRefundFail.setVisibility(View.VISIBLE);
            reasonFail.setText(bean.getMSG());
        }
        reasonDetail.setText(bean.getREFUND_DETAIL());

        x.image().bind(icon, bean.getPICS());
    }
}
