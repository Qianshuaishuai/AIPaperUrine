package com.babyraising.aipaperurine.ui.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.EditImgResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_baby_id)
public class BabyIdActivity extends BaseActivity {

    private UserBean userBean;

    @ViewInject(R.id.baby_id)
    private TextView babyId;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.sure)
    private void sure(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();


    }

    private void addMember() {
        if (babyId.getText().toString().length() == 0) {
            T.s("宝宝id不能为空");
            return;
        }
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_ADDMEMBER);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("CODE", babyId.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                EditImgResponse response = gson.fromJson(result, EditImgResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("添加成功");
                        finish();
                        break;
                    default:
                        T.s("添加失败");
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
