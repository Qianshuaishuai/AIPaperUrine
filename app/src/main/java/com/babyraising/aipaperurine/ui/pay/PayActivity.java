package com.babyraising.aipaperurine.ui.pay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_pay)
public class PayActivity extends BaseActivity {

    private int payType = 1;

    @ViewInject(R.id.rb_pay_1)
    private CheckBox rbPay1;

    @ViewInject(R.id.rb_pay_2)
    private CheckBox rbPay2;

    @Event(R.id.rb_pay_1)
    private void rbPay1Click(View view){
        if (payType != 1){
            rbPay1.setChecked(true);
            rbPay2.setChecked(false);
            payType = 1;
        }
    }

    @Event(R.id.rb_pay_2)
    private void rbPay2Click(View view){
        if (payType != 2){
            rbPay1.setChecked(false);
            rbPay2.setChecked(true);
            payType = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
