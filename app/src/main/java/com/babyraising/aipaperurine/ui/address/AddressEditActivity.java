package com.babyraising.aipaperurine.ui.address;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.AddressInfoResponse;
import com.babyraising.aipaperurine.response.AddressResponse;
import com.babyraising.aipaperurine.response.CommonResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_address_edit)
public class AddressEditActivity extends BaseActivity {

    private UserBean userBean;
    private String addressId;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.save)
    private void save(View view) {
        if (TextUtils.isEmpty(addressId)) {

        } else {

        }
    }

    @Event(R.id.city_layout)
    private void cityLayoutClick(View view) {

    }

    @ViewInject(R.id.name)
    private EditText name;

    @ViewInject(R.id.phone)
    private EditText phone;

    @ViewInject(R.id.detail_address)
    private EditText detailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        addressId = intent.getStringExtra("addressId");

        if (!TextUtils.isEmpty(addressId)) {
            RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_ADDRESSINFO);
            params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
            params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
            params.addQueryStringParameter("ADDRESS_ID", addressId);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    AddressInfoResponse response = gson.fromJson(result, AddressInfoResponse.class);
                    switch (response.getResult()) {
                        case 0:
                            name.setText(response.getData().getCNAME());
                            phone.setText(response.getData().getCPHONE());
                            detailAddress.setText(response.getData().getADDRESS());
                            break;
                        default:
                            T.s("获取地址详情失败");
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

    private void EditAddress(String addressId, String cName, String cPhone, String address, String proviceCode, String cityCode, String districtCode, String detail) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_ADDADDRESS);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("ADDRESS_ID", addressId);
        params.addQueryStringParameter("CNAME", cName);
        params.addQueryStringParameter("CPHONE", cPhone);
        params.addQueryStringParameter("ADDRESS", address);
        params.addQueryStringParameter("PROVICE_CODE", proviceCode);
        params.addQueryStringParameter("CITY_CODE", cityCode);
        params.addQueryStringParameter("DISTRICT_CODE", districtCode);
        params.addQueryStringParameter("DETAIL", detail);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("编辑成功");
                        break;
                    default:
                        T.s("编辑失败");
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

    private void AddAddress(String cName, String cPhone, String address, String proviceCode, String cityCode, String districtCode, String detail, String isDefault) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_ADDADDRESS);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("CNAME", cName);
        params.addQueryStringParameter("CPHONE", cPhone);
        params.addQueryStringParameter("ADDRESS", address);
        params.addQueryStringParameter("PROVICE_CODE", proviceCode);
        params.addQueryStringParameter("CITY_CODE", cityCode);
        params.addQueryStringParameter("DISTRICT_CODE", districtCode);
        params.addQueryStringParameter("DETAIL", detail);
        params.addQueryStringParameter("ISDEFAULT", isDefault);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("添加成功");
                        break;
                    default:
                        T.s("添加失败");
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
