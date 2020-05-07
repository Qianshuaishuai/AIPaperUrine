package com.xinxin.aicare.ui.pay;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.PayOrderWechatBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.bean.ali.PayDetailResult;
import com.xinxin.aicare.bean.ali.PayResult;
import com.xinxin.aicare.event.PayResultEvent;
import com.xinxin.aicare.response.PayOrderAliResponse;
import com.xinxin.aicare.response.PayOrderWechatResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.Map;

@ContentView(R.layout.activity_pay)
public class PayActivity extends BaseActivity {

    private UserBean userBean;

    private int payType = 1;
    private String payTypeName = "weixin";

    private String YuyueID = "";
    private String REALPAY = "0.0";

    private AlertDialog payTipDialog;

    private IWXAPI api;

    @ViewInject(R.id.rb_pay_1)
    private CheckBox rbPay1;

    @ViewInject(R.id.rb_pay_2)
    private CheckBox rbPay2;

    @ViewInject(R.id.price)
    private TextView price;

    @Event(R.id.layout_back)
    private void back(View view) {
        payTipDialog.show();
    }

    @Event(R.id.sure)
    private void sure(View view) {
        goToPay();
    }

    @Event(R.id.rb_pay_1)
    private void rbPay1Click(View view) {
        if (payType != 1) {
            rbPay1.setChecked(true);
            rbPay2.setChecked(false);
            payType = 1;
            payTypeName = "weixin";
        }
    }

    @Event(R.id.rb_pay_2)
    private void rbPay2Click(View view) {
        if (payType != 2) {
            rbPay1.setChecked(false);
            rbPay2.setChecked(true);
            payType = 2;
            payTypeName = "alipay";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initPayTipDialog();
    }

    private void initData() {

        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        YuyueID = intent.getStringExtra("yuyueId");
        REALPAY = intent.getStringExtra("realPay");

        if (TextUtils.isEmpty(YuyueID)) {
            T.s("获取订单信息失败");
            finish();
            return;
        }

        DecimalFormat df = new DecimalFormat("#0.00");
        float realPay = Float.parseFloat(REALPAY);
        price.setText("￥" + df.format(realPay));
    }

    private void goToPay() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PAYORDER);
        params.addQueryStringParameter("YUYUE_ID", YuyueID);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("PAYMETHOD", payTypeName);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                if (payType == 1) {
                    PayOrderWechatResponse response = gson.fromJson(result, PayOrderWechatResponse.class);

                    switch (response.getResult()) {
                        case 0:
                            payForWechat(response.getData());
                            break;
                        default:
                            T.s("获取微信支付信息失败");
                            break;
                    }
                } else if (payType == 2) {
                    PayOrderAliResponse response = gson.fromJson(result, PayOrderAliResponse.class);

                    switch (response.getResult()) {
                        case 0:
                            payForAli(response.getData().getOrderString());
                            break;
                        default:
                            T.s("获取支付宝信息失败");
                            break;
                    }
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

    private void payForWechat(PayOrderWechatBean bean) {
        //初始化注册
        api = WXAPIFactory.createWXAPI(this, null, false);
        api.registerApp(bean.getAppid());

        PayReq req = new PayReq();
        req.appId = bean.getAppid();
        req.partnerId = bean.getPartnerid();
        req.prepayId = bean.getPrepayid();
        req.nonceStr = bean.getNoncestr();
        req.timeStamp = bean.getTimestamp();
        req.packageValue = bean.getPackageStr();
        req.sign = bean.getSign();
        req.extData = "app data"; // optional
        api.sendReq(req);
    }

    private void payForAli(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = Constant.PAY_FOR_ORDER;
                msg.obj = result;
                payHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler payHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                Gson gson = new Gson();
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                PayDetailResult result = gson.fromJson(resultInfo, PayDetailResult.class);

                String orderNo = result.getAlipay_trade_app_pay_response().getOut_trade_no();
                String payTotal = result.getAlipay_trade_app_pay_response().getTotal_amount();
                T.s("支付成功");
                startPayResultActivity();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                startHomeActivity();
                T.s("支付失败");

            }
        }

        ;
    };

    private void startPayResultActivity() {
        finish();
        Intent intent = new Intent(this, PayResultActivity.class);
        intent.putExtra("realPay", REALPAY);
        startActivity(intent);
    }

    private void initPayTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        payTipDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                payTipDialog.cancel();
            }
        });

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                payTipDialog.cancel();
                finish();
            }
        });

        payTipDialog.setCancelable(false);
//        tipDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PayResultEvent event) {
        startPayResultActivity();
    }
}
