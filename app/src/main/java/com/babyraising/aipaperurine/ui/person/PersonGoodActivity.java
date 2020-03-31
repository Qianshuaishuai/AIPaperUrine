package com.babyraising.aipaperurine.ui.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.PersonalCollectAdapter;
import com.babyraising.aipaperurine.adapter.PersonalGoodAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.PersonalActBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.PersonalActResponse;
import com.babyraising.aipaperurine.ui.main.FindDetailActivity;
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

@ContentView(R.layout.activity_person_good)
public class PersonGoodActivity extends BaseActivity {

    private String INFO_TYPE = "2";
    private UserBean bean;
    private List<PersonalActBean> goodList;
    private PersonalGoodAdapter adapter;

    @ViewInject(R.id.rv_person_good)
    private RecyclerView rvPersonGood;

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
        goodList = new ArrayList<>();
        adapter = new PersonalGoodAdapter(goodList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPersonGood.setAdapter(adapter);
        rvPersonGood.setLayoutManager(manager);
        adapter.setOnItemClickListener(new PersonalGoodAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startInfoDetailActivity(goodList.get(position).getINFORMATION_ID());
            }

        });
    }

    private void startInfoDetailActivity(String infoId) {
        Intent intent = new Intent(this, FindDetailActivity.class);
        intent.putExtra("infoId", infoId);
        startActivity(intent);
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
                        goodList.clear();
                        for (int a = 0; a < response.getData().size(); a++) {
                            goodList.add(response.getData().get(a));
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        T.s("获取点赞列表失败");
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
