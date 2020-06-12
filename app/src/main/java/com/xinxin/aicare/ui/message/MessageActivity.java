package com.xinxin.aicare.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.DeviceMessageAdapter;
import com.xinxin.aicare.adapter.OfficeMessageAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.MessageBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.MessageResponse;
import com.xinxin.aicare.ui.info.RemindDetailActivity;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_message)
public class MessageActivity extends BaseActivity {

    private DeviceMessageAdapter dma;
    private OfficeMessageAdapter oma;

    private List<MessageBean> dmaList;
    private List<MessageBean> omaList;

    private UserBean bean;

    @ViewInject(R.id.device_message)
    private RecyclerView dmRecycleview;

    @ViewInject(R.id.office_message)
    private RecyclerView omRecycleview;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @ViewInject(R.id.office_message_tip)
    private TextView officeMessageTip;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    private String memberId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");

        if (!TextUtils.isEmpty(memberId)) {

            RequestParams aParams = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
            aParams.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
            aParams.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
            aParams.addQueryStringParameter("TYPE", "2");
            aParams.addQueryStringParameter("MEMBER_ID", memberId);
            x.http().post(aParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                    Gson gson = new Gson();
                    MessageResponse response = gson.fromJson(result, MessageResponse.class);
                    switch (response.getResult()) {
                        case 0:
                            dmaList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                dmaList.add(response.getData().get(m));
                            }
                            dma.notifyDataSetChanged();
                            break;

                        default:
                            T.s("获取设备消息失败");
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

            officeMessageTip.setVisibility(View.GONE);
            omRecycleview.setVisibility(View.GONE);
        } else {
            RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
            params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
            params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
            params.addQueryStringParameter("TYPE", "2");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                    Gson gson = new Gson();
                    MessageResponse response = gson.fromJson(result, MessageResponse.class);
                    switch (response.getResult()) {
                        case 0:
                            dmaList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                dmaList.add(response.getData().get(m));
                            }
                            dma.notifyDataSetChanged();
                            break;

                        default:
                            T.s("获取设备消息失败");
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


            RequestParams aParams = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
            aParams.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
            aParams.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
            aParams.addQueryStringParameter("TYPE", "1");
            x.http().post(aParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    MessageResponse response = gson.fromJson(result, MessageResponse.class);
                    System.out.println(result);
                    switch (response.getResult()) {
                        case 0:
                            omaList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                omaList.add(response.getData().get(m));
                            }
                            oma.notifyDataSetChanged();
                            break;

                        default:
                            T.s("获取系统消息失败");
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

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(memberId)) {

            RequestParams aParams = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
            aParams.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
            aParams.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
            aParams.addQueryStringParameter("TYPE", "2");
            aParams.addQueryStringParameter("MEMBER_ID", memberId);
            x.http().post(aParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                    Gson gson = new Gson();
                    MessageResponse response = gson.fromJson(result, MessageResponse.class);
                    switch (response.getResult()) {
                        case 0:
                            dmaList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                dmaList.add(response.getData().get(m));
                            }
                            dma.notifyDataSetChanged();
                            break;

                        default:
                            T.s("获取设备消息失败");
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

            officeMessageTip.setVisibility(View.GONE);
            omRecycleview.setVisibility(View.GONE);
        } else {
            RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
            params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
            params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
            params.addQueryStringParameter("TYPE", "2");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                    Gson gson = new Gson();
                    MessageResponse response = gson.fromJson(result, MessageResponse.class);
                    switch (response.getResult()) {
                        case 0:
                            dmaList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                dmaList.add(response.getData().get(m));
                            }
                            dma.notifyDataSetChanged();
                            break;

                        default:
                            T.s("获取设备消息失败");
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


            RequestParams aParams = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
            aParams.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
            aParams.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
            aParams.addQueryStringParameter("TYPE", "1");
            x.http().post(aParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    MessageResponse response = gson.fromJson(result, MessageResponse.class);
                    System.out.println(result);
                    switch (response.getResult()) {
                        case 0:
                            omaList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                omaList.add(response.getData().get(m));
                            }
                            oma.notifyDataSetChanged();
                            break;

                        default:
                            T.s("获取系统消息失败");
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

    private void initView() {
        dmaList = new ArrayList<>();
        dma = new DeviceMessageAdapter(dmaList);
        LinearLayoutManager dmaManager = new LinearLayoutManager(this);
        dmRecycleview.setLayoutManager(dmaManager);
        dmRecycleview.setAdapter(dma);
        dma.setOnItemClickListener(new DeviceMessageAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startRemindDetailActivity(dmaList.get(position).getMESSAGE_ID());
            }
        });

        omaList = new ArrayList<>();
        oma = new OfficeMessageAdapter(omaList);
        LinearLayoutManager omaManager = new LinearLayoutManager(this);
        omRecycleview.setLayoutManager(omaManager);
        omRecycleview.setAdapter(oma);
        oma.setOnItemClickListener(new OfficeMessageAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startRemindDetailActivity(omaList.get(position).getMESSAGE_ID());
            }
        });
    }

    private void startRemindDetailActivity(String messageId) {
        Intent intent = new Intent(this, RemindDetailActivity.class);
        intent.putExtra("message-id", messageId);
        startActivity(intent);
    }
}
