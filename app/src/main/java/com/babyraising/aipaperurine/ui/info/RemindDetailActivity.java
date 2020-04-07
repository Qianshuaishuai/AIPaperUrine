package com.babyraising.aipaperurine.ui.info;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.MessageInfoResponse;
import com.babyraising.aipaperurine.response.MessageResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_remind_detail)
public class RemindDetailActivity extends BaseActivity {

    private UserBean userBean;
    private String MESSAGE_ID = "";

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @ViewInject(R.id.remind_detail_tip)
    private TextView remindDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        MESSAGE_ID = intent.getStringExtra("message-id");

        if (MESSAGE_ID == "") {
            T.s("获取信息详情失败");
            finish();
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGEINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MESSAGE_ID", MESSAGE_ID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                MessageInfoResponse response = gson.fromJson(result, MessageInfoResponse.class);
                switch (response.getResult()) {
                    case 0:
                        remindDetail.setText(response.getData().getDETAIL());
                        break;

                    default:
                        T.s("获取信息详情失败");
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
}
