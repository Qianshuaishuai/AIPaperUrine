package com.babyraising.aipaperurine.ui.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_good)
public class GoodActivity extends BaseActivity {

    @ViewInject(R.id.buy)
    private Button buyBt;

    @ViewInject(R.id.param_layout)
    private RelativeLayout paramLayout;

    @ViewInject(R.id.buy_layout)
    private RelativeLayout buyLayout;

    @Event(R.id.sure)
    private void sure(View view) {
        buyBt.setVisibility(View.VISIBLE);
        paramLayout.setVisibility(View.GONE);
    }

    @Event(R.id.buy)
    private void buy(View view) {
        buyBt.setVisibility(View.GONE);
        buyLayout.setVisibility(View.VISIBLE);
    }

    @Event(R.id.param_simple_layout)
    private void showParamLayout(View view) {
        buyBt.setVisibility(View.GONE);
        paramLayout.setVisibility(View.VISIBLE);
    }

    @Event(R.id.sure_buy)
    private void sureBuy(View view) {
        buyBt.setVisibility(View.VISIBLE);
        buyLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
