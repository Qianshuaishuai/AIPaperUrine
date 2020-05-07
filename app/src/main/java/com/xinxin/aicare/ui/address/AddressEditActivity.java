package com.xinxin.aicare.ui.address;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.AddressInfoBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.AddressInfoResponse;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.ui.picker.CityPickerDialog;
import com.xinxin.aicare.ui.picker.Util;
import com.xinxin.aicare.ui.picker.address.City;
import com.xinxin.aicare.ui.picker.address.County;
import com.xinxin.aicare.ui.picker.address.Province;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;


import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@ContentView(R.layout.activity_address_edit)
public class AddressEditActivity extends BaseActivity {

    private UserBean userBean;
    private String addressId = "";
    private String isDefault = "1";
    private AddressInfoBean bean = new AddressInfoBean();

    private ArrayList<Province> provinces = new ArrayList<Province>();

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @ViewInject(R.id.city_tip)
    private TextView cityTip;

    @ViewInject(R.id.main_layout)
    private LinearLayout mainLayout;

    @ViewInject(R.id.layout_default)
    private LinearLayout layoutDefault;

    @Event(R.id.save)
    private void save(View view) {

        if (name.getText().toString().length() == 0) {
            T.s("收货人不能为空");
            return;
        }

        if (phone.getText().toString().length() == 0) {
            T.s("联系电话不能为空");
            return;
        }

        if (TextUtils.isEmpty(bean.getPROVICE_CODE())) {
            T.s("请先选择所在城市");
            return;
        }

        if (detailAddress.getText().toString().length() == 0) {
            T.s("详细地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(addressId)) {
            String addressTip = bean.getPROVICE_CODE() + " " + bean.getCITY_CODE() + " " + bean.getDISTRICT_CODE();
            AddAddress(name.getText().toString(), phone.getText().toString(), addressTip, bean.getPROVICE_CODE(), bean.getCITY_CODE(), bean.getDISTRICT_CODE(), detailAddress.getText().toString(), isDefault);
        } else {
            EditAddress(bean.getADDRESS_ID(), name.getText().toString(), phone.getText().toString(), bean.getADDRESS(), bean.getPROVICE_CODE(), bean.getCITY_CODE(), bean.getDISTRICT_CODE(), detailAddress.getText().toString());
        }
    }

    @Event(R.id.city_layout)
    private void cityLayoutClick(View view) {
        if (provinces.size() > 0) {
            showAddressDialog();
        } else {
            new InitAreaTask(this).execute(0);
        }
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
                if (b) {
                    isDefault = "2";
                } else {
                    isDefault = "1";
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
                            bean = response.getData();
                            name.setText(response.getData().getCNAME());
                            phone.setText(response.getData().getCPHONE());
                            detailAddress.setText(response.getData().getADDRESS());
                            isDefault = response.getData().getISDEFAULT();
                            cityTip.setText(bean.getPROVICE_CODE() + " " + bean.getCITY_CODE() + " " + bean.getDISTRICT_CODE());
                            layoutDefault.setVisibility(View.GONE);
                            switch (response.getData().getISDEFAULT()) {
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

    }

    private void EditAddress(String addressId, String cName, String cPhone, String address, String proviceCode, String cityCode, String districtCode, String detail) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITADDRESS);
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
                        finish();
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
        params.addQueryStringParameter("DETAIL", detail);
        params.addQueryStringParameter("ISDEFAULT", isDefault);
        if (!TextUtils.isEmpty(address)) {
            params.addQueryStringParameter("ADDRESS", address);
        } else {
            params.addQueryStringParameter("ADDRESS", "");
        }

        if (!TextUtils.isEmpty(proviceCode)) {
            params.addQueryStringParameter("PROVICE_CODE", proviceCode);
        } else {
            params.addQueryStringParameter("PROVICE_CODE", "无省编号");
        }

        if (!TextUtils.isEmpty(cityCode)) {
            params.addQueryStringParameter("CITY_CODE", cityCode);
        } else {
            params.addQueryStringParameter("CITY_CODE", "无市编号");
        }

        if (!TextUtils.isEmpty(districtCode)) {
            params.addQueryStringParameter("DISTRICT_CODE", districtCode);
        } else {
            params.addQueryStringParameter("DISTRICT_CODE", "无区编号");
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        T.s("添加成功");
                        finish();
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

    private void showAddressDialog() {
        new CityPickerDialog(this, provinces, null, null, null,
                new CityPickerDialog.onCityPickedListener() {

                    @Override
                    public void onPicked(Province selectProvince,
                                         City selectCity, County selectCounty) {
                        StringBuilder address = new StringBuilder();
                        address.append(
                                selectProvince != null ? selectProvince
                                        .getAreaName() : "")
                                .append(selectCity != null ? selectCity
                                        .getAreaName() : "")
                                .append(selectCounty != null ? selectCounty
                                        .getAreaName() : "");
                        String text = selectCounty != null ? selectCounty
                                .getAreaName() : "";
                        String addressTip = "";
                        if (selectProvince != null) {
                            if (selectProvince != null) {
                                addressTip = addressTip + selectProvince.getAreaName();
                                bean.setPROVICE_CODE(selectProvince.getAreaName());
                            }
                        }

                        if (selectCity != null) {
                            if (selectCity.getAreaName() != null) {
                                addressTip = addressTip + " " + selectCity.getAreaName();
                                bean.setCITY_CODE(selectCity.getAreaName());
                            }
                        }

                        if (selectCounty != null) {
                            if (selectCounty.getAreaName() != null) {
                                addressTip = addressTip + " " + selectCounty.getAreaName();
                                bean.setDISTRICT_CODE(selectCounty.getAreaName());
                            }

                        }
                        cityTip.setText(addressTip);
                    }
                }).show();
    }

    private class InitAreaTask extends AsyncTask<Integer, Integer, Boolean> {

        Context mContext;

        Dialog progressDialog;

        public InitAreaTask(Context context) {
            mContext = context;
            progressDialog = Util.createLoadingDialog(mContext, "请稍等...", true,
                    0);
        }

        @Override
        protected void onPreExecute() {

            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (provinces.size() > 0) {
                showAddressDialog();
            } else {
                Toast.makeText(mContext, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            String address = null;
            InputStream in = null;
            try {
                in = mContext.getResources().getAssets().open("address.txt");
                byte[] arrayOfByte = new byte[in.available()];
                in.read(arrayOfByte);
                address = EncodingUtils.getString(arrayOfByte, "UTF-8");
                JSONArray jsonList = new JSONArray(address);
                Gson gson = new Gson();
                for (int i = 0; i < jsonList.length(); i++) {
                    try {
                        provinces.add(gson.fromJson(jsonList.getString(i),
                                Province.class));
                    } catch (Exception e) {
                    }
                }
                return true;
            } catch (Exception e) {

            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
            return false;
        }

    }
}
