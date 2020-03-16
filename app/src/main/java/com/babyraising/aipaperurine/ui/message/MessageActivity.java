package com.babyraising.aipaperurine.ui.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.DeviceMessageAdapter;
import com.babyraising.aipaperurine.adapter.OfficeMessageAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.MessageBean;
import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.MessageResponse;
import com.babyraising.aipaperurine.response.RegisterResponse;
import com.babyraising.aipaperurine.util.T;
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

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getApplication()).getUserInfo();

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


        RequestParams aParams = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
        aParams.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        aParams.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        aParams.addQueryStringParameter("TYPE", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MessageResponse response = gson.fromJson(result, MessageResponse.class);
                switch (response.getResult()) {
                    case 0:
                        omaList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            omaList.add(response.getData().get(m));
                        }
                        oma.notifyDataSetChanged();
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

            }
        });
    }
}
