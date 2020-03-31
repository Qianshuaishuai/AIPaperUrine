package com.babyraising.aipaperurine.ui.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.response.AboutUsResponse;
import com.babyraising.aipaperurine.response.VersionResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_version)
public class VersionActivity extends BaseActivity {

    @ViewInject(R.id.logo)
    private ImageView logo;

    @ViewInject(R.id.version)
    private TextView version;

    @Event(R.id.layout_back)
    private void layoutBackClick(View view) {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_VERSION);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                VersionResponse response = gson.fromJson(result, VersionResponse.class);
                switch (response.getResult()) {
                    case 0:
                        version.setText(response.getData().getAPPVERSION());
                        break;

                    default:
                        T.s("获取版本信息失败");
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
