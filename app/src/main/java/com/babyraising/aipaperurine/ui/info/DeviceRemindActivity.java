package com.babyraising.aipaperurine.ui.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.DeviceMessageAdapter;
import com.babyraising.aipaperurine.adapter.DeviceRemindAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.MessageBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.MessageResponse;
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

@ContentView(R.layout.activity_device_remind)
public class DeviceRemindActivity extends BaseActivity {

    private DeviceRemindAdapter adapter;

    private List<MessageBean> list;

    private UserBean bean;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
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
