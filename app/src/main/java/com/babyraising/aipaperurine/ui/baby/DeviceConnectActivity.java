package com.babyraising.aipaperurine.ui.baby;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.AboutUsResponse;
import com.babyraising.aipaperurine.response.CommonResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_device_connect)
public class DeviceConnectActivity extends BaseActivity {

    private UserBean userBean;
    private String memberId = "";

    @Event(R.id.layout_back)
    private void back(View view) {
        tipDialog.show();
    }

    @ViewInject(R.id.layout1)
    private RelativeLayout layout1;

    @ViewInject(R.id.layout2)
    private RelativeLayout layout2;

    @ViewInject(R.id.layout3)
    private RelativeLayout layout3;

    @ViewInject(R.id.layout4)
    private RelativeLayout layout4;

    @ViewInject(R.id.layout5)
    private RelativeLayout layout5;

    private AlertDialog tipDialog;
    private Timer timer;
    private TimerTask timerTask;

    private int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initTipDialog();
    }

    private void initView() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (mode) {
                            case 1:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.GONE);
                                layout3.setVisibility(View.GONE);
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 2:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.GONE);
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 3:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 4:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.VISIBLE);
                                layout1.setVisibility(View.GONE);
                                break;
                            case 5:
                                layout5.setVisibility(View.VISIBLE);
                                layout4.setVisibility(View.VISIBLE);
                                layout3.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.VISIBLE);
                                layout1.setVisibility(View.VISIBLE);
                                break;
                        }


                        if (mode == 5) {
                            mode = 1;
                        } else {
                            mode = mode + 1;
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 350);
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");
    }

    private void bindDevice(String code){
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_BINDDEVICE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberId);
        params.addQueryStringParameter("DEVICE_CODE", code);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        T.s("绑定设备成功");
                        break;

                    default:
                        T.s("绑定设备失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误处理:"+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void initTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        tipDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);
        TextView title = (TextView) view.findViewById(R.id.title);

        title.setText("确定要退出设备连接?");

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tipDialog.cancel();
            }
        });

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tipDialog.cancel();
                finish();
            }
        });

        tipDialog.setCancelable(false);
//        tipDialog.show();
    }

}
