package com.xinxin.aicare.ui.info;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.MemberChangeInfoBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.event.BindBabyIdSuccessEvent;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.MemberChangeInfoResponse;
import com.xinxin.aicare.response.UseragreementResponse;
import com.xinxin.aicare.response.VersionResponse;
import com.xinxin.aicare.util.T;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_urine_more_detail)
public class UrineMoreDetailActivity extends BaseActivity {

    private UserBean userBean;
    private String DEVICEDATAID = "";
    private TextView dialogTitle;

    private AlertDialog scoreRuleDialog;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @ViewInject(R.id.create_time)
    private TextView createTime;

    @ViewInject(R.id.ns_time)
    private TextView nsTime;

    @ViewInject(R.id.full_time)
    private TextView fullTime;

    @ViewInject(R.id.down_time)
    private TextView downTime;

    @ViewInject(R.id.iv_star1)
    private ImageView ivStar1;

    @ViewInject(R.id.iv_star2)
    private ImageView ivStar2;

    @ViewInject(R.id.iv_star3)
    private ImageView ivStar3;

    @ViewInject(R.id.comment)
    private TextView comment;

    @ViewInject(R.id.advice)
    private TextView advice;

    @ViewInject(R.id.growth_count)
    private TextView growthCount;

    @ViewInject(R.id.score_count)
    private TextView scoreCount;

    @ViewInject(R.id.value)
    private TextView valueTv;

    @Event(R.id.score_rule)
    private void scoreRule(View view){
        scoreRuleDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initScoreRuleDialog();
        getScoreRule();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        DEVICEDATAID = intent.getStringExtra("dataId");

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERCHANGEINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("DEVICEDATA_ID", DEVICEDATAID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MemberChangeInfoResponse response = gson.fromJson(result, MemberChangeInfoResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        updateView(response.getData());
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

    private void getScoreRule(){
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_USERAGREEMENT);
        params.addQueryStringParameter("TYPE", 2);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                UseragreementResponse response = gson.fromJson(result, UseragreementResponse.class);
                switch (response.getResult()) {
                    case 0:
                        dialogTitle.setText(Html.fromHtml(response.getData().getUSERAGREEMENT_INFO()));
                        break;
                    default:

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


    private void updateView(MemberChangeInfoBean data) {
        if (TextUtils.isEmpty(data.getCREATETIME())) {
            createTime.setText("未有数据");
        } else {
            createTime.setText(data.getCREATETIME());
        }

        if (TextUtils.isEmpty(data.getURINE_WETNESS_TIME())) {
            nsTime.setText("未有数据");
        } else {
            nsTime.setText(data.getURINE_WETNESS_TIME());
        }

        if (TextUtils.isEmpty(data.getURINE_FULL_TIME())) {
            fullTime.setText("未有数据");
        } else {
            fullTime.setText(data.getURINE_FULL_TIME());
        }

        if (TextUtils.isEmpty(data.getDOWNTIME())) {
            downTime.setText("未有数据");
        } else {
            downTime.setText(data.getDOWNTIME());
        }

        if (TextUtils.isEmpty(data.getCOMMENT())) {
            comment.setText("未有数据");
        } else {
            comment.setText(data.getCOMMENT());
        }

        if (TextUtils.isEmpty(data.getVIEW())) {
            advice.setText("未有数据");
        } else {
            advice.setText(data.getVIEW());
        }

        if (TextUtils.isEmpty(data.getGROWTH())) {
            growthCount.setText("未有数据");
        } else {
            growthCount.setText("+" + data.getGROWTH());
        }

        if (TextUtils.isEmpty(data.getPOINT())) {
            scoreCount.setText("未有数据");
        } else {
            scoreCount.setText("+" + data.getPOINT());
        }

        if (TextUtils.isEmpty(data.getNL())) {
            valueTv.setText("未有数据");
        } else {
            valueTv.setText("+" + data.getNL() + "ml");
        }

        int starCount = Integer.parseInt(data.getSTAR());

        switch (starCount) {
            case 0:
                ivStar1.setVisibility(View.GONE);
                ivStar2.setVisibility(View.GONE);
                ivStar3.setVisibility(View.GONE);
                break;
            case 1:
                ivStar1.setVisibility(View.VISIBLE);
                ivStar2.setVisibility(View.GONE);
                ivStar3.setVisibility(View.GONE);
                break;
            case 2:
                ivStar1.setVisibility(View.VISIBLE);
                ivStar2.setVisibility(View.VISIBLE);
                ivStar3.setVisibility(View.GONE);
                break;
            case 3:
                ivStar1.setVisibility(View.VISIBLE);
                ivStar2.setVisibility(View.VISIBLE);
                ivStar3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initScoreRuleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_score_rule, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        scoreRuleDialog = builder.create();
        dialogTitle = (TextView)view.findViewById(R.id.title);
        Button sureButton = (Button) view.findViewById(R.id.sure);

        dialogTitle.setText("获取数据中......");

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                scoreRuleDialog.cancel();
            }
        });

        scoreRuleDialog.setCancelable(false);
//        tipDialog.show();
    }
}
