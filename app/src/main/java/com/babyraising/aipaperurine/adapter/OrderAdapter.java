package com.babyraising.aipaperurine.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.CouponMoreBean;
import com.babyraising.aipaperurine.bean.YuYueBean;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<YuYueBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumTxt, timeTxt, nameTxt, paramsTxt, statusTipTxt, countTipTxt, priceTipTxt;
        ImageView icon;
        Button bt1, bt2, bt3, bt4;

        public ViewHolder(View view) {
            super(view);
            orderNumTxt = (TextView) view.findViewById(R.id.order_num);
            timeTxt = (TextView) view.findViewById(R.id.time);
            nameTxt = (TextView) view.findViewById(R.id.name);
            paramsTxt = (TextView) view.findViewById(R.id.params);
            statusTipTxt = (TextView) view.findViewById(R.id.status_tip);
            countTipTxt = (TextView) view.findViewById(R.id.count_tip);
            priceTipTxt = (TextView) view.findViewById(R.id.price_tip);

            icon = (ImageView) view.findViewById(R.id.icon);
            bt1 = (Button) view.findViewById(R.id.bt_1);
            bt2 = (Button) view.findViewById(R.id.bt_2);
            bt3 = (Button) view.findViewById(R.id.bt_3);
            bt4 = (Button) view.findViewById(R.id.bt_4);
        }

    }

    public OrderAdapter(List<YuYueBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

        YuYueBean data = mList.get(position);

        switch (data.getPAYSTATE()){

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
