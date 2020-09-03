package com.xinxin.aicare.ui.info;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.DeviceRemindAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.MessageBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.MessageResponse;
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

@ContentView(R.layout.activity_device_remind)
public class DeviceRemindActivity extends BaseActivity {

    private DeviceRemindAdapter adapter;

    private List<MessageBean> list;

    private UserBean bean;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.read_all)
    private void readAll(View view){
        readAll();
    }

    @ViewInject(R.id.rv_remind)
    private RecyclerView rvRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        list = new ArrayList<>();
        adapter = new DeviceRemindAdapter(list);
        LinearLayoutManager dmaManager = new LinearLayoutManager(this);
        rvRemind.setLayoutManager(dmaManager);
        rvRemind.setAdapter(adapter);
        adapter.setOnItemClickListener(new DeviceRemindAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    private void readAll(){
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MESSAGELIST);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("一键已读成功");
                        break;

                    default:
                        T.s("一键已读失败");
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
                        list.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            list.add(response.getData().get(m));
                        }
                        adapter.notifyDataSetChanged();
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
}
