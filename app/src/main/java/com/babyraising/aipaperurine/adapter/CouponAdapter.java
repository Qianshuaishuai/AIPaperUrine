package com.babyraising.aipaperurine.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.CouponMoreBean;
import com.babyraising.aipaperurine.bean.MessageBean;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private List<CouponMoreBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView priceTxt, typeTxt, conditionTxt, validTxt, useStatusTxt, tipTxt;
        RelativeLayout useStatusLayout;

        public ViewHolder(View view) {
            super(view);
            tipTxt = (TextView) view.findViewById(R.id.tip);
            typeTxt = (TextView) view.findViewById(R.id.type);
            priceTxt = (TextView) view.findViewById(R.id.price);
            conditionTxt = (TextView) view.findViewById(R.id.condition);
            validTxt = (TextView) view.findViewById(R.id.valid);
            useStatusTxt = (TextView) view.findViewById(R.id.use_status);
            useStatusLayout = (RelativeLayout) view.findViewById(R.id.layout_use_status);
        }

    }

    public CouponAdapter(List<CouponMoreBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_person_coupon, parent, false);
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

        holder.tipTxt.setText("优惠券使用详情：" + mList.get(position).getTIP());
        holder.validTxt.setText("有效期至" + mList.get(position).getENDDATE());

        if (TextUtils.isEmpty(mList.get(position).getTHRESHOLDAMT())) {
            holder.conditionTxt.setText("没限额条件");
        } else {
            holder.conditionTxt.setText("满" + mList.get(position).getTHRESHOLDAMT() + "可用");
        }

        switch (mList.get(position).getTYPE()) {
            case "1":
                holder.typeTxt.setText("现金券");
                holder.priceTxt.setText(mList.get(position).getAMT());
                break;
            case "2":
                holder.typeTxt.setText("折扣券");
                holder.priceTxt.setText(mList.get(position).getDISCOUNT());
                break;
        }

        switch (mList.get(position).getSTATE()) {
            case "1":
                holder.useStatusTxt.setText("未使用");
                holder.useStatusLayout.setBackgroundResource(R.drawable.shape_coupon_bt_bg);
                holder.useStatusTxt.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "2":
                holder.useStatusTxt.setText("已使用");
                holder.useStatusLayout.setBackgroundResource(R.drawable.shape_coupon_bt_used_bg);
                holder.useStatusTxt.setTextColor(Color.parseColor("#F87EB7"));
                break;
            case "3":
                holder.useStatusTxt.setText("已过期");
                holder.useStatusLayout.setBackgroundResource(R.drawable.shape_coupon_bt_used_bg);
                holder.useStatusTxt.setTextColor(Color.parseColor("#F87EB7"));
                break;
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
