package com.babyraising.aipaperurine.ui.person;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.IntegralAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.GrowthPointBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.GrowthPointListResponse;
import com.babyraising.aipaperurine.response.ListMyYuyueResponse;
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

@ContentView(R.layout.activity_integral)
public class IntegralActivity extends BaseActivity {

    private String integralType = "1";
    private DatePickerDialog yearMonthDatePickerDialog;

    private List<GrowthPointBean> integralList;
    private IntegralAdapter adapter;
    private UserBean userBean;

    @ViewInject(R.id.rv_integral)
    private RecyclerView rvIntegral;

    @ViewInject(R.id.all_count)
    private TextView allCount;

    @ViewInject(R.id.tv_date)
    private TextView tvDate;

    @Event(R.id.date)
    private void dateClick(View view) {
        yearMonthDatePickerDialog.show();
//        int SDKVersion = this.getSDKVersionNumber();// 获取系统版本
//        DatePicker dp = findDatePicker((ViewGroup) yearMonthDatePickerDialog.getWindow()
//                .getDecorView());// 设置弹出年月日
//        if (dp != null) {
//            if (SDKVersion < 11) {
//                ((ViewGroup) dp.getChildAt(0)).getChildAt(1).setVisibility(
//                        View.GONE);
//            } else if (SDKVersion > 14) {
//                //只显示年月
//                ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
//                        .getChildAt(1).setVisibility(View.GONE);//.getChildAt(0)
//            }
//        }
    }

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

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        updateList();
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
                        tvDate.setText(year + "." + month);
                    }
                },
                mYear, mMonth, mDay);
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
                        T.s("获取积分明细失败");
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

    /**
     * 从当前Dialog中查找DatePicker子控件
     *
     * @param group
     * @return
     */
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    /**
     * 获取系统SDK版本
     *
     * @return
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }
}
