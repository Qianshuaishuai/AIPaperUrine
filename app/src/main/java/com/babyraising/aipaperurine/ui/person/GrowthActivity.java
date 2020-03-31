package com.babyraising.aipaperurine.ui.person;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.IntegralAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.GrowthPointBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.GrowthPointListResponse;
import com.babyraising.aipaperurine.util.T;
import com.babyraising.aipaperurine.view.YearMonthDatePickerDialog;
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
    private YearMonthDatePickerDialog yearMonthDatePickerDialog;

    private List<GrowthPointBean> integralList;
    private IntegralAdapter adapter;
    private UserBean userBean;

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

        yearMonthDatePickerDialog = new YearMonthDatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText(year + "." + month);
                    }
                },
                mYear, mMonth, mDay);
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        updateList();
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
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        integralList.clear();
                        for (int m = 0; m < response.getData().size(); m++) {
                            integralList.add(response.getData().get(m));
                        }
                        adapter.notifyDataSetChanged();
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
}
