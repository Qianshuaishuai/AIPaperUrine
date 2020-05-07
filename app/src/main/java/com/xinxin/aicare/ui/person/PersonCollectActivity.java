package com.xinxin.aicare.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.PersonalCollectAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.PersonalActBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.PersonalActResponse;
import com.xinxin.aicare.ui.main.FindDetailActivity;
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

@ContentView(R.layout.activity_person_collect)
public class PersonCollectActivity extends BaseActivity {

    private String INFO_TYPE = "1";
    private UserBean bean;
    private List<PersonalActBean> collectList;
    private PersonalCollectAdapter adapter;

    @ViewInject(R.id.rv_person_collect)
    private RecyclerView rvPersonCollect;

    @Event(R.id.more)
    private void more(View view) {

    }

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        updateList();
    }

    private void initView() {
        collectList = new ArrayList<>();
        adapter = new PersonalCollectAdapter(collectList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPersonCollect.setAdapter(adapter);
        rvPersonCollect.setLayoutManager(manager);
        adapter.setOnItemClickListener(new PersonalCollectAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startInfoDetailActivity(collectList.get(position).getINFORMATION_ID());
            }
        });
    }

    private void updateList() {
        bean = ((PaperUrineApplication) getApplication()).getUserInfo();
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PERSONALACTLIST);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("TYPE", INFO_TYPE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PersonalActResponse response = gson.fromJson(result, PersonalActResponse.class);
                switch (response.getResult()) {
                    case 0:
                        collectList.clear();
                        for (int a = 0; a < response.getData().size(); a++) {
                            collectList.add(response.getData().get(a));
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        T.s("获取收藏列表失败");
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

    private void startInfoDetailActivity(String infoId) {
        Intent intent = new Intent(this, FindDetailActivity.class);
        intent.putExtra("infoId", infoId);
        startActivity(intent);
    }
}
