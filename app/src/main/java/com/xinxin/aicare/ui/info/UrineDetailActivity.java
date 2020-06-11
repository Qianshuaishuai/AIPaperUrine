package com.xinxin.aicare.ui.info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.UrineRecordAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.MemberDataCal1ListBean;
import com.xinxin.aicare.bean.MemberDataCal4Bean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.MemberDataCal1Response;
import com.xinxin.aicare.response.MemberDataCal2Response;
import com.xinxin.aicare.response.MemberDataCal3Response;
import com.xinxin.aicare.response.MemberDataCal4Response;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;
import com.lixs.charts.LineChartView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ContentView(R.layout.activity_urine_detail)
public class UrineDetailActivity extends BaseActivity {

    private int type = 1;
    private List<MemberDataCal4Bean> cal4BeanList;
    private UrineRecordAdapter adapter;
    private UserBean userBean;
    private String START_DATE = "";
    private String END_DATE = "";
    private String MEMBER_ID = "";
    private DatePickerDialog yearMonthDatePickerDialog;

    private List<Double> dataList;
    private List<String> descriptionList;

    private int dateType = 1;

    private int currentDay = 0;
    private int currentMonth = 0;
    private int currentYear = 0;

    @ViewInject(R.id.group_type)
    private RadioGroup typeGroup;

    @ViewInject(R.id.lineView)
    private LineChartView chartView;

    @ViewInject(R.id.lineView1)
    private LineChartView chartView1;

    @ViewInject(R.id.lineView2)
    private LineChartView chartView2;

    @ViewInject(R.id.tab_date_tv)
    private TextView tabDateTv;

    @ViewInject(R.id.rv_detail4)
    private RecyclerView rvDetail;

    @ViewInject(R.id.group_date)
    private RadioGroup dateGroup;

    @ViewInject(R.id.layout_detail1)
    private LinearLayout detail1Layout;

    @ViewInject(R.id.data_detail1)
    private TextView dataDetail1;

    @ViewInject(R.id.data_detail2)
    private TextView dataDetail2;

    @ViewInject(R.id.data_detail3)
    private TextView dataDetail3;

    @ViewInject(R.id.data_detail_tip1)
    private TextView dataDetailTip1;

    @ViewInject(R.id.data_detail2_tip1)
    private TextView dataDetail2Tip1;

    @ViewInject(R.id.data_detail3_tip1)
    private TextView dataDetail3Tip1;

    @ViewInject(R.id.data_detail_tip2)
    private TextView dataDetailTip2;

    @ViewInject(R.id.data_detail2_tip2)
    private TextView dataDetail2Tip2;

    @ViewInject(R.id.data_detail3_tip2)
    private TextView dataDetail3Tip2;

    @ViewInject(R.id.layout_detail2)
    private LinearLayout detail2Layout;

    @ViewInject(R.id.layout_detail3)
    private LinearLayout detail3Layout;

    @ViewInject(R.id.layout_detail4)
    private LinearLayout detail4Layout;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.layout_date_picker)
    private void layoutDatePicker(View view) {
        yearMonthDatePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initDatePicker();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        Intent intent = getIntent();
        MEMBER_ID = intent.getStringExtra("memberId");
        dataList = new ArrayList<>();
        descriptionList = new ArrayList<>();

        chartView.setCanClickAnimation(true);
        chartView.setDatas(dataList, descriptionList);
        chartView.setShowNum(0);

        chartView1.setCanClickAnimation(true);
        chartView1.setDatas(dataList, descriptionList);
        chartView1.setShowNum(0);

        chartView2.setCanClickAnimation(true);
        chartView2.setDatas(dataList, descriptionList);
        chartView2.setShowNum(0);


        updateList();
    }

    private void initDatePicker() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);

        currentDay = mDay;
        currentMonth = mMonth;
        currentYear = mYear;

        START_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
        END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;

        yearMonthDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        String result = year + "-" + (month + 1) + "-" + dayOfMonth;
                        tabDateTv.setText(result);

                        currentDay = dayOfMonth;
                        currentMonth = month;
                        currentYear = year;
                        updateListForDate();

                    }
                },
                mYear, mMonth, mDay);
    }

    private void updateListForDate() {
        int mYear = currentYear;
        int mMonth = currentMonth;
        int mDay = currentDay;
        switch (dateType) {
            case 1:
                dateType = 1;
                START_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                break;
            case 2:
                dateType = 2;
                END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                if (mDay <= 7) {
                    mDay = 30 - (7 - mDay);
                    mMonth = mMonth - 1;
                } else {
                    mDay = mDay - 7;
                }
                START_DATE = mYear + "-" + (mMonth + 1) + "-" + (mDay);
                break;
            case 3:
                dateType = 3;
                END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                if (mDay <= 15) {
                    mDay = 30 - (15 - mDay);
                    mMonth = mMonth - 1;
                } else {
                    mDay = mDay - 15;
                }
                START_DATE = mYear + "-" + (mMonth + 1) + "-" + (mDay);
                break;
            case 4:
                dateType = 4;
                END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                if (mMonth <= 0) {
                    mMonth = 11;
                    mYear = mYear - 1;
                } else {
                    mMonth = mMonth - 1;
                }
                START_DATE = mYear + "-" + (mMonth + 1) + "-" + (mDay);
                break;
        }

        updateList();
    }

    private void initView() {
        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.bt1_type:
                        type = 1;
                        detail1Layout.setVisibility(View.VISIBLE);
                        detail2Layout.setVisibility(View.GONE);
                        detail3Layout.setVisibility(View.GONE);
                        detail4Layout.setVisibility(View.GONE);
                        break;
                    case R.id.bt2_type:
                        type = 2;
                        detail1Layout.setVisibility(View.GONE);
                        detail2Layout.setVisibility(View.VISIBLE);
                        detail3Layout.setVisibility(View.GONE);
                        detail4Layout.setVisibility(View.GONE);
                        break;
                    case R.id.bt3_type:
                        type = 3;
                        detail1Layout.setVisibility(View.GONE);
                        detail2Layout.setVisibility(View.GONE);
                        detail3Layout.setVisibility(View.VISIBLE);
                        detail4Layout.setVisibility(View.GONE);
                        break;
                    case R.id.bt4_type:
                        type = 4;
                        detail1Layout.setVisibility(View.GONE);
                        detail2Layout.setVisibility(View.GONE);
                        detail3Layout.setVisibility(View.GONE);
                        detail4Layout.setVisibility(View.VISIBLE);
                        break;
                }
                updateList();
            }
        });

        dateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int mYear = currentYear;
                int mMonth = currentMonth;
                int mDay = currentDay;

                switch (i) {
                    case R.id.bt1_date:
                        dateType = 1;
                        START_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                        END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                        break;
                    case R.id.bt2_date:
                        dateType = 2;
                        END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                        if (mDay <= 7) {
                            mDay = 30 - (7 - mDay);
                            mMonth = mMonth - 1;
                        } else {
                            mDay = mDay - 7;
                        }
                        START_DATE = mYear + "-" + (mMonth + 1) + "-" + (mDay);
                        break;
                    case R.id.bt3_date:
                        dateType = 3;
                        END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                        if (mDay <= 15) {
                            mDay = 30 - (15 - mDay);
                            mMonth = mMonth - 1;
                        } else {
                            mDay = mDay - 15;
                        }
                        START_DATE = mYear + "-" + (mMonth + 1) + "-" + (mDay);
                        break;
                    case R.id.bt4_date:
                        dateType = 4;
                        END_DATE = mYear + "-" + (mMonth + 1) + "-" + mDay;
                        if (mMonth <= 0) {
                            mMonth = 11;
                            mYear = mYear - 1;
                        } else {
                            mMonth = mMonth - 1;
                        }
                        START_DATE = mYear + "-" + (mMonth + 1) + "-" + (mDay);
                        break;
                }

                updateList();
            }
        });

        cal4BeanList = new ArrayList<>();
        adapter = new UrineRecordAdapter(cal4BeanList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter.setOnItemClickListener(new UrineRecordAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        rvDetail.setLayoutManager(manager);
        rvDetail.setAdapter(adapter);
    }

    private void drawChart(List<MemberDataCal1ListBean> list, int type) {
        dataList.clear();
        descriptionList.clear();
        if (list.size() > 0) {
            for (int d = 0; d < list.size(); d++) {
                dataList.add(Double.valueOf(list.get(d).getCNT()));
                descriptionList.add(list.get(d).getTIME());
            }
//            System.out.println(dataList.size());
            switch (type) {
                case 1:
                    chartView.setVisibility(View.VISIBLE);
                    chartView.setCanClickAnimation(true);
                    chartView.setDatas(dataList, descriptionList);
                    chartView.setShowNum(dataList.size() + 1);
                    break;
                case 2:
                    chartView1.setCanClickAnimation(true);
                    chartView1.setDatas(dataList, descriptionList);
                    chartView1.setShowNum(7);
                    break;
                case 3:
                    chartView2.setCanClickAnimation(true);
                    chartView2.setDatas(dataList, descriptionList);
                    chartView2.setShowNum(7);
                    break;
            }

        }

    }

    private String translateDate(String date) {
        String[] splits = date.split("-");
        if (splits.length > 2) {
            String year = splits[0];
            String month = splits[1];
            String day = splits[2];
            String newDate = "";
            if (month.length() == 1) {
                month = "0" + month;
            }
            if (day.length() == 1) {
                day = "0" + day;
            }

            newDate = year + "-" + month + "-" + day;
            return newDate;
        }

        return date;
    }

    private void updateList() {
        START_DATE = translateDate(START_DATE);
        END_DATE = translateDate(END_DATE);
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERDATACAL);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", MEMBER_ID);
        params.addQueryStringParameter("STARTDATE", START_DATE);
        params.addQueryStringParameter("ENDDATE", END_DATE);
        params.addQueryStringParameter("TYPE", type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                System.out.println(result);
                switch (type) {
                    case 1:
                        MemberDataCal1Response response1 = gson.fromJson(result, MemberDataCal1Response.class);
                        switch (response1.getResult()) {
                            case 0:
                                dataDetail1.setText("共使用尿布" + response1.getData().getDIAPER_CNT() + "片，当日系统用户平均使用尿片" + response1.getData().getAVG_DIAPER_CNT() + "片");
                                dataDetailTip1.setText(response1.getData().getCOMMENT());
                                dataDetailTip2.setText(response1.getData().getVIEW());
                                drawChart(response1.getData().getDATALIST(), 1);
                                break;
                            default:
                                T.s("获取用片统计失败");
                                break;
                        }
                        break;
                    case 2:
                        MemberDataCal2Response response2 = gson.fromJson(result, MemberDataCal2Response.class);
                        switch (response2.getResult()) {
                            case 0:
                                dataDetail2.setText("宝宝平均总尿量" + response2.getData().getRECOMMEND_VOLUME());
                                dataDetail2Tip1.setText(response2.getData().getCOMMENT());
                                dataDetail2Tip2.setText(response2.getData().getVIEW());
                                break;
                            default:
                                T.s("获取尿量统计失败");
                                break;
                        }
                        break;
                    case 3:
                        MemberDataCal3Response response3 = gson.fromJson(result, MemberDataCal3Response.class);
                        switch (response3.getResult()) {
                            case 0:
                                dataDetail3.setText("没有参考值");
                                dataDetail3Tip1.setText(response3.getData().getCOMMENT());
                                dataDetail3Tip2.setText(response3.getData().getVIEW());
                                break;
                            default:
                                T.s("获取强提醒次数失败");
                                break;
                        }
                        break;
                    case 4:
                        MemberDataCal4Response response4 = gson.fromJson(result, MemberDataCal4Response.class);
                        switch (response4.getResult()) {
                            case 0:
                                cal4BeanList.clear();
                                for (int a = 0; a < response4.getData().size(); a++) {
                                    cal4BeanList.add(response4.getData().get(a));
                                }
                                adapter.notifyDataSetChanged();
                                break;
                            default:
                                T.s("获取更换记录失败");
                                break;
                        }
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
