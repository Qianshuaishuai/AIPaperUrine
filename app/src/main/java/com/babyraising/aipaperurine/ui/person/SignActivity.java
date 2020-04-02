package com.babyraising.aipaperurine.ui.person;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.SignInfoResponse;
import com.babyraising.aipaperurine.response.SigninResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_sign)
public class SignActivity extends BaseActivity {

    private UserBean userBean;
    private AlertDialog tipDialog;
    private TextView detail;

    @ViewInject(R.id.sign_day)
    private TextView signDay;

    @ViewInject(R.id.integral)
    private TextView integral;

    @ViewInject(R.id.sign_complete)
    private Button signComplete;

    @ViewInject(R.id.rv_sign)
    private RecyclerView rvSign;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.sign_complete)
    private void signComplete(View view) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SIGNIN);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                SigninResponse response = gson.fromJson(result, SigninResponse.class);
                switch (response.getResult()) {
                    case 0:
                        detail.setText("+" + response.getData().getPOINT() + "分");
                        tipDialog.show();
                        break;
                    default:
                        T.s("签到失败");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initTipDialog();
    }

    private void initTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_sign_success, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        tipDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        detail = (TextView) view.findViewById(R.id.detail);

        Button btClose = (Button) view.findViewById(R.id.bt_close);

        btClose.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tipDialog.cancel();
            }
        });

        tipDialog.setCancelable(false);
//        tipDialog.show();
    }

    private void initView() {

    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SIGNININFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                SignInfoResponse response = gson.fromJson(result, SignInfoResponse.class);
                switch (response.getResult()) {
                    case 0:
                        signDay.setText(response.getData().getCONTINUITY_SIGNIN_NUM());

                        switch (response.getData().getHAS_SIGNIN()) {
                            case "1":
                                signComplete.setClickable(true);
                                signComplete.setText("签到");
                                signComplete.setTextColor(getResources().getColor(R.color.colorWhite));
                                signComplete.setBackgroundResource(R.drawable.shape_sign_bt_bg);
                                break;
                            case "2":
                                signComplete.setClickable(false);
                                signComplete.setText("已签到");
                                signComplete.setTextColor(getResources().getColor(R.color.colorSign));
                                signComplete.setBackgroundResource(R.drawable.shape_sign_bt_selected_bg);
                                break;
                        }
                        break;
                    default:
                        T.s("获取签到信息失败");
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
