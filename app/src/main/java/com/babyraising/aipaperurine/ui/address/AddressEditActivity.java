package com.babyraising.aipaperurine.ui.address;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_address_edit)
public class AddressEditActivity extends BaseActivity {

    //申明对象
    CityPickerView mPicker = new CityPickerView();
    private UserBean userBean;
    private String addressId;
    private String isDefault = "1";

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
        //显示
        mPicker.showCityPicker();
    }

    @ViewInject(R.id.cb_default)
    private CheckBox cbDefault;

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
        cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isDefault = "1";
                }else{
                    isDefault = "2";
                }
            }
        });
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
                            isDefault = response.getData().getISDEFAULT();
                            switch (response.getData().getISDEFAULT()){
                                case "1":
                                    cbDefault.setChecked(true);
                                    break;
                                case "2":
                                    cbDefault.setChecked(false);
                                    break;
                            }
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

        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

//监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份province
                //城市city
                //地区district
            }

            @Override
            public void onCancel() {

            }
        });

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
