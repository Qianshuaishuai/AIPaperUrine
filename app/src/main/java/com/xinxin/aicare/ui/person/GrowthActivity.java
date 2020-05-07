package com.xinxin.aicare.ui.person;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.IntegralAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.GrowthPointBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.GrowthPointListResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ContentView(R.layout.activity_growth)
public class GrowthActivity extends BaseActivity {

    private String integralType = "2";
    private DatePickerDialog yearMonthDatePickerDialog;

    private List<GrowthPointBean> integralList;
    private List<GrowthPointBean> allList;
    private IntegralAdapter adapter;
    private UserBean userBean;
    private String currentDate;

    @ViewInject(R.id.rv_growth)
    private RecyclerView rvIntegral;

    @ViewInject(R.id.tv_date)
    private TextView tvDate;

    @Event(R.id.date)
    private void dateClick(View view) {
        yearMonthDatePickerDialog.show();
    }
//
//    @ViewInject(R.id.all_count)
//    private TextView allCount;

    @Event(R.id.layout_back)
    private void layoutBackClick(View view) {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initDatePicker();
    }

    private void initDatePicker() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);

        yearMonthDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText(year + "." + (month + 1));
                        if (month + 1 < 10) {
                            translateDate(year + "-" + "0" + (month + 1));
                        } else {
                            translateDate(year + "-" + (month + 1));
                        }

                    }
                },
                mYear, mMonth, mDay);
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        updateList();

        Calendar c = Calendar.getInstance();//

        tvDate.setText(c.get(Calendar.YEAR) + "." + (c.get(Calendar.MONTH) + 1));
        if (c.get(Calendar.MONTH) + 1 < 10) {
            currentDate = c.get(Calendar.YEAR) + "-" + "0" + (c.get(Calendar.MONTH) + 1);
        } else {
            currentDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1);
        }
    }

    private void updateList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GROWTHPOINTLIST);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("TYPE", integralType);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GrowthPointListResponse response = gson.fromJson(result, GrowthPointListResponse.class);
                switch (response.getResult()) {
                    case 0:
                        allList.clear();
                        integralList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            allList.add(response.getData().get(m));
                            integralList.add(response.getData().get(m));
                        }
//                        translateDate(currentDate);
//                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        T.s("获取成长明细失败");
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
        integralList = new ArrayList<>();
        allList = new ArrayList<>();
        adapter = new IntegralAdapter(integralList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvIntegral.setLayoutManager(manager);
        rvIntegral.setAdapter(adapter);
        adapter.setOnItemClickListener(new IntegralAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    private void translateDate(String date) {
        integralList.clear();
        for (int i = 0; i < allList.size(); i++) {
            if (allList.get(i).getCREATETIME().contains(date)) {
                integralList.add(allList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
