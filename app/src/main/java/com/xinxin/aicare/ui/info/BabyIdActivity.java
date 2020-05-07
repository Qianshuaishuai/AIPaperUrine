package com.xinxin.aicare.ui.info;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.util.T;
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
        addMember();
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
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        T.s("添加成功");
                        finish();
                        break;
                    default:
                        T.s("宝宝ID不存在");
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
