package com.babyraising.aipaperurine.ui.address;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.AddressAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.AddressBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.AddressResponse;
import com.babyraising.aipaperurine.response.CouponResponse;
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

@ContentView(R.layout.activity_address_manager)
public class AddressManagerActivity extends BaseActivity {

    private UserBean userBean;
    private List<AddressBean> addressBeanList;
    private AddressAdapter addressAdapter;

    @ViewInject(R.id.rv_address)
    private RecyclerView rvAddress;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.build)
    private void build(View view) {
        startAddressActivity("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_ADDRESSLIST);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                AddressResponse response = gson.fromJson(result, AddressResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        addressBeanList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            addressBeanList.add(response.getData().get(m));
                        }
                        addressAdapter.notifyDataSetChanged();
                        break;
                    default:
                        T.s("获取地址列表失败");
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

    private void initView() {
        addressBeanList = new ArrayList<>();
        addressAdapter = new AddressAdapter(addressBeanList);
        addressAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startAddressActivity(addressBeanList.get(position).getADDRESS_ID());
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvAddress.setAdapter(addressAdapter);
        rvAddress.setLayoutManager(manager);
    }

    private void startAddressActivity(String addressId) {
        Intent intent = new Intent(this, AddressEditActivity.class);
        if (!TextUtils.isEmpty(addressId)) {
            intent.putExtra("addressId", addressId);
        }
        startActivity(intent);
    }
}
