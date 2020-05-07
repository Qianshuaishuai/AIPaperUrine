package com.xinxin.aicare.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.YuYueBean;
import com.xinxin.aicare.ui.order.OrderActivity;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<YuYueBean> mList;
    private OrderActivity context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumTxt, timeTxt, nameTxt, paramsTxt, statusTipTxt, countTipTxt, priceTipTxt;
        ImageView icon;
        Button bt1, bt2, bt3, bt4;
        RecyclerView rvOrderGood;
        LinearLayout btLayout;

        public ViewHolder(View view) {
            super(view);
            orderNumTxt = (TextView) view.findViewById(R.id.order_num);
            timeTxt = (TextView) view.findViewById(R.id.time);

            countTipTxt = (TextView) view.findViewById(R.id.count_tip);
            priceTipTxt = (TextView) view.findViewById(R.id.price_tip);

            bt1 = (Button) view.findViewById(R.id.bt_1);
            bt2 = (Button) view.findViewById(R.id.bt_2);
            bt3 = (Button) view.findViewById(R.id.bt_3);
            bt4 = (Button) view.findViewById(R.id.bt_4);

            rvOrderGood = (RecyclerView) view.findViewById(R.id.rv_order_good);
            btLayout = (LinearLayout) view.findViewById(R.id.bt_layout);
        }

    }

    public OrderAdapter(List<YuYueBean> mList, OrderActivity context) {
        this.mList = mList;
        this.context = context;
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

        DecimalFormat df = new DecimalFormat("#0.00");
        float realPay = Float.parseFloat(mList.get(position).getREALPAY());
        holder.orderNumTxt.setText(mList.get(position).getPAYNO());
        holder.timeTxt.setText(mList.get(position).getCREATETIME());
        holder.countTipTxt.setText("共" + mList.get(position).getGOODS().size() + "件商品 合计：");
        holder.priceTipTxt.setText("￥" + df.format(realPay));

        switch (mList.get(position).getPAYSTATE()) {
            case "1":
                holder.btLayout.setVisibility(View.VISIBLE);
                holder.bt3.setVisibility(View.VISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.cancelOrder(mList.get(position).getYUYUE_ID());
                    }
                });

                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToPay(mList.get(position).getYUYUE_ID());
                    }
                });
                break;
            case "2":
                holder.btLayout.setVisibility(View.GONE);
                break;
            case "3":
                holder.btLayout.setVisibility(View.GONE);
                break;
        }

        OrderGoodAdapter adapter = new OrderGoodAdapter(mList.get(position).getGOODS(), context);
        LinearLayoutManager manager = new LinearLayoutManager(context);

        holder.rvOrderGood.setAdapter(adapter);
        holder.rvOrderGood.setLayoutManager(manager);
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
