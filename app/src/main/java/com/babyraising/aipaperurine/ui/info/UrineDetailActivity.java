package com.babyraising.aipaperurine.ui.info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.UrineRecordAdapter;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.MemberDataCal4Bean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CourseResponse;
import com.babyraising.aipaperurine.response.MemberDataCal1Response;
import com.babyraising.aipaperurine.response.MemberDataCal2Response;
import com.babyraising.aipaperurine.response.MemberDataCal3Response;
import com.babyraising.aipaperurine.response.MemberDataCal4Response;
import com.babyraising.aipaperurine.util.T;
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

    @ViewInject(R.id.group_type)
    private RadioGroup typeGroup;

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
        initData();
        initDatePicker();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        Intent intent = getIntent();
        MEMBER_ID = intent.getStringExtra("memberId");

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
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        String result = year + "-" + month + "-" + dayOfMonth;
                        tabDateTv.setText(result);
                    }
                },
                mYear, mMonth, mDay);
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

    private void updateList() {
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
                switch (type) {
                    case 1:
                        MemberDataCal1Response response1 = gson.fromJson(result, MemberDataCal1Response.class);
                        switch (response1.getResult()) {
                            case 0:
                                dataDetail1.setText("共使用尿布" + response1.getData().getDIAPER_CNT() + "片，当日系统用户平均使用尿片" + response1.getData().getAVG_DIAPER_CNT() + "片");
                                dataDetailTip1.setText(response1.getData().getCOMMENT());
                                dataDetailTip2.setText(response1.getData().getVIEW());
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
