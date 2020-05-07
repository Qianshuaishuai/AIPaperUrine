package com.xinxin.aicare.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

@ContentView(R.layout.activity_change_password)
public class ChangePasswordActivity extends BaseActivity {

    private UserBean userBean;

    @ViewInject(R.id.input_old_ps)
    private EditText oldPs;

    @ViewInject(R.id.input_new_ps)
    private EditText newPs;

    @ViewInject(R.id.input_sure_ps)
    private EditText surePs;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.forget)
    private void forget(View view) {

    }

    @Event(R.id.complete)
    private void complete(View view) {
        String oldPassword = oldPs.getText().toString();
        String newPassword = newPs.getText().toString();
        String surePassword = surePs.getText().toString();

        if (oldPassword.length() <= 0) {
            T.s("旧密码不能为空");
            return;
        }

        if (newPassword.length() <= 0) {
            T.s("新密码不能为空");
            return;
        }

        if (surePassword.length() <= 0) {
            T.s("确认密码不能为空");
            return;
        }

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITPSW);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("OLD_PSW", oldPassword);
        params.addQueryStringParameter("NEW_PSW", newPassword);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("修改成功");
                        finish();
                        break;
                    default:
                        T.s("修改失败");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
    }
}
