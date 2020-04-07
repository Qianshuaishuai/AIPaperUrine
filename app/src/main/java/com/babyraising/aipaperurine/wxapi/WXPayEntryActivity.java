package com.babyraising.aipaperurine.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.event.PayResultEvent;
import com.babyraising.aipaperurine.util.T;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

//        api = WXAPIFactory.createWXAPI(this, Constant.WeChatAppId);
//        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case -1:
                T.s("支付出错");
                break;
            case -2:
                T.s("支付取消");
                break;
            case 0:
                T.s("支付成功");
                EventBus.getDefault().post(new PayResultEvent());
                break;
        }
        finish();
    }
}