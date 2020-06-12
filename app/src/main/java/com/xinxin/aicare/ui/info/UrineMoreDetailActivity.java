package com.xinxin.aicare.ui.info;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.event.BindBabyIdSuccessEvent;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.MemberChangeInfoResponse;
import com.xinxin.aicare.util.T;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

@ContentView(R.layout.activity_urine_more_detail)
public class UrineMoreDetailActivity extends BaseActivity {

    private UserBean userBean;
    private String DEVICEDATAID = "";

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

        Intent intent=getIntent();
        DEVICEDATAID = intent.getStringExtra("dataId");

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERCHANGEINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("DEVICEDATA_ID", DEVICEDATAID);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MemberChangeInfoResponse response = gson.fromJson(result, MemberChangeInfoResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:

                        break;
                    default:
                        T.s("获取更换详情失败");
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
