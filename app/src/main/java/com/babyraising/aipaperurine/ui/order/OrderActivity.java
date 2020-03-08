package com.babyraising.aipaperurine.ui.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseActivity {

    private int orderType = 1;

    @ViewInject(R.id.view_order_1)
    private View viewOrder1;
    @ViewInject(R.id.view_order_2)
    private View viewOrder2;
    @ViewInject(R.id.view_order_3)
    private View viewOrder3;
    @ViewInject(R.id.view_order_4)
    private View viewOrder4;
    @ViewInject(R.id.view_order_5)
    private View viewOrder5;

    @ViewInject(R.id.tv_order_1)
    private TextView tvOrder1;
    @ViewInject(R.id.tv_order_2)
    private TextView tvOrder2;
    @ViewInject(R.id.tv_order_3)
    private TextView tvOrder3;
    @ViewInject(R.id.tv_order_4)
    private TextView tvOrder4;
    @ViewInject(R.id.tv_order_5)
    private TextView tvOrder5;

    @ViewInject(R.id.tv_order_1_normal)
    private TextView tvOrderNormal1;
    @ViewInject(R.id.tv_order_2_normal)
    private TextView tvOrderNormal2;
    @ViewInject(R.id.tv_order_3_normal)
    private TextView tvOrderNormal3;
    @ViewInject(R.id.tv_order_4_normal)
    private TextView tvOrderNormal4;
    @ViewInject(R.id.tv_order_5_normal)
    private TextView tvOrderNormal5;

    @Event(R.id.layout_order_1)
    private void orderLayout1Click(View view) {
        if (orderType != 1) {
            viewOrder1.setVisibility(View.VISIBLE);
            tvOrder1.setVisibility(View.VISIBLE);
            tvOrderNormal1.setVisibility(View.GONE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 1;
        }
    }

    @Event(R.id.layout_order_2)
    private void orderLayout2Click(View view) {
        if (orderType != 2) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.VISIBLE);
            tvOrder2.setVisibility(View.VISIBLE);
            tvOrderNormal2.setVisibility(View.GONE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 2;
        }
    }

    @Event(R.id.layout_order_3)
    private void orderLayout3Click(View view) {
        if (orderType != 3) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.VISIBLE);
            tvOrder3.setVisibility(View.VISIBLE);
            tvOrderNormal3.setVisibility(View.GONE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 3;
        }
    }

    @Event(R.id.layout_order_4)
    private void orderLayout4Click(View view) {
        if (orderType != 4) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.VISIBLE);
            tvOrder4.setVisibility(View.VISIBLE);
            tvOrderNormal4.setVisibility(View.GONE);

            viewOrder5.setVisibility(View.GONE);
            tvOrder5.setVisibility(View.GONE);
            tvOrderNormal5.setVisibility(View.VISIBLE);

            orderType = 4;
        }
    }

    @Event(R.id.layout_order_5)
    private void orderLayout5Click(View view) {
        if (orderType != 5) {
            viewOrder1.setVisibility(View.GONE);
            tvOrder1.setVisibility(View.GONE);
            tvOrderNormal1.setVisibility(View.VISIBLE);

            viewOrder2.setVisibility(View.GONE);
            tvOrder2.setVisibility(View.GONE);
            tvOrderNormal2.setVisibility(View.VISIBLE);

            viewOrder3.setVisibility(View.GONE);
            tvOrder3.setVisibility(View.GONE);
            tvOrderNormal3.setVisibility(View.VISIBLE);

            viewOrder4.setVisibility(View.GONE);
            tvOrder4.setVisibility(View.GONE);
            tvOrderNormal4.setVisibility(View.VISIBLE);

            viewOrder5.setVisibility(View.VISIBLE);
            tvOrder5.setVisibility(View.VISIBLE);
            tvOrderNormal5.setVisibility(View.GONE);

            orderType = 5;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
