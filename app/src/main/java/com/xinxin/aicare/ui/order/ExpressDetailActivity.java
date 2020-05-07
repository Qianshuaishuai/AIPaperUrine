package com.xinxin.aicare.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.ShowMyYuyueLogisticsBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.ShowMyYuyueLogisticsResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_express_detail)
public class ExpressDetailActivity extends BaseActivity {

    private String yuyueId = "";

    private UserBean userBean;
    private ShowMyYuyueLogisticsBean bean;

    @ViewInject(R.id.status_tip)
    private TextView statusTip;

    @ViewInject(R.id.order_no)
    private TextView orderNo;

    @ViewInject(R.id.express_no)
    private TextView expressNo;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.express)
    private TextView express;

    @ViewInject(R.id.express_name)
    private TextView expressName;

    @ViewInject(R.id.rv_express)
    private RecyclerView rvExpress;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
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
        yuyueId = intent.getStringExtra("yuyueId");

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SHOWMYYUYUELOGISTICS);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUECARD_ID", yuyueId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShowMyYuyueLogisticsResponse response = gson.fromJson(result, ShowMyYuyueLogisticsResponse.class);
                switch (response.getResult()) {
                    case 0:
                        bean = response.getData();
                        updateView();
                        break;
                    default:
                        T.s("获取物流详情失败");
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
        expressNo.setText(bean.getTNO());
        expressName.setText(bean.getTNAME());

        switch (bean.getSTATE()) {
            case "2":
                statusTip.setText("在途中");
                break;
            case "3":
                statusTip.setText("签收");
                break;
            case "4":
                statusTip.setText("问题件");
                break;
        }
    }
}
