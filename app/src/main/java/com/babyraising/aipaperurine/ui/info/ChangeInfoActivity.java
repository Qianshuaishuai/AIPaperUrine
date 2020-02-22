package com.babyraising.aipaperurine.ui.info;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import cn.aigestudio.datepicker.views.DatePicker;

@ContentView(R.layout.activity_change_info)
public class ChangeInfoActivity extends BaseActivity {

    @ViewInject(R.id.date_picker)
    private DatePicker datePicker;

    @ViewInject(R.id.layout_date_picker)
    private RelativeLayout layoutDatePicker;

    @ViewInject(R.id.card_date)
    private TextView cardDate;

    @Event(R.id.card_date)
    private void cardDataClick(View view) {
        layoutDatePicker.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatePicker();
    }

    private void initDatePicker() {
        datePicker.setDate(2020, 2);
        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                String result = "";
                Iterator iterator = date.iterator();
                while (iterator.hasNext()) {
                    result += iterator.next();
                    if (iterator.hasNext()) {
                        result += "\n";
                    }
                }

                cardDate.setText(result);
                if (layoutDatePicker.getVisibility() == View.VISIBLE) {
                    layoutDatePicker.setVisibility(View.GONE);
                }
            }
        });
    }

}
