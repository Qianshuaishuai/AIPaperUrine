package com.babyraising.aipaperurine.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.GoodBean;
import com.babyraising.aipaperurine.ui.order.OrderActivity;

import org.xutils.x;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private List<GoodBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumTxt, timeTxt, nameTxt, paramsTxt, statusTipTxt, countTipTxt, priceTipTxt;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            orderNumTxt = (TextView) view.findViewById(R.id.order_num);
            timeTxt = (TextView) view.findViewById(R.id.time);
            nameTxt = (TextView) view.findViewById(R.id.name);
            paramsTxt = (TextView) view.findViewById(R.id.params);
            statusTipTxt = (TextView) view.findViewById(R.id.status_tip);
            countTipTxt = (TextView) view.findViewById(R.id.count);
            priceTipTxt = (TextView) view.findViewById(R.id.price_tip);

            icon = (ImageView) view.findViewById(R.id.icon);
        }

    }

    public OrderDetailAdapter(List<GoodBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_order_detail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
        DecimalFormat df = new DecimalFormat("#0.00");
        float price = Float.parseFloat(mList.get(position).getPRICE());
        x.image().bind(holder.icon, mList.get(position).getPICS());
        holder.nameTxt.setText(mList.get(position).getTITLE());
        holder.priceTipTxt.setText(df.format(price));
        holder.countTipTxt.setText("x" + mList.get(position).getNUM());

        String params = "";
        if (!TextUtils.isEmpty(mList.get(position).getBRAND_SIZE())) {
            params = params + mList.get(position).getBRAND_SIZE();
        }

        if (!TextUtils.isEmpty(mList.get(position).getATTRIBUTE_VALUE1())) {
            params = params + mList.get(position).getATTRIBUTE_VALUE1();
        }


        if (!TextUtils.isEmpty(mList.get(position).getATTRIBUTE_VALUE2())) {
            params = params + mList.get(position).getATTRIBUTE_VALUE2();
        }


        if (!TextUtils.isEmpty(mList.get(position).getATTRIBUTE_VALUE3())) {
            params = params + mList.get(position).getATTRIBUTE_VALUE3();
        }

        holder.paramsTxt.setText(params);

        switch (mList.get(position).getSTATE()) {
            case "1":
                holder.statusTipTxt.setText("待支付");
                break;
            case "2":
                holder.statusTipTxt.setText("已取消");
                break;
            case "3":
                holder.statusTipTxt.setText("待发货");
                break;
            case "4":
                holder.statusTipTxt.setText("已发货");
                break;
            case "5":
                holder.statusTipTxt.setText("已完成");
                break;
            case "6":
                holder.statusTipTxt.setText("退款中");
                break;
            case "7":
                holder.statusTipTxt.setText("退款成功");
                break;
            case "8":
                holder.statusTipTxt.setText("退款失败");
                break;
            case "9":
                holder.statusTipTxt.setText("退款申请中");
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
