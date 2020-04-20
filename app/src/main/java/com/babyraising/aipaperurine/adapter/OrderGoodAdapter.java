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
import com.babyraising.aipaperurine.bean.YuYueBean;
import com.babyraising.aipaperurine.ui.order.OrderActivity;

import org.xutils.x;

import java.text.DecimalFormat;
import java.util.List;

public class OrderGoodAdapter extends RecyclerView.Adapter<OrderGoodAdapter.ViewHolder> {

    private List<GoodBean> mList;
    private OrderActivity context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumTxt, timeTxt, nameTxt, paramsTxt, statusTipTxt, countTipTxt, priceTipTxt;
        ImageView icon;
        Button bt1, bt2, bt3, bt4;
        LinearLayout btLayout;

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
            bt1 = (Button) view.findViewById(R.id.bt_1);
            bt2 = (Button) view.findViewById(R.id.bt_2);
            bt3 = (Button) view.findViewById(R.id.bt_3);
            bt4 = (Button) view.findViewById(R.id.bt_4);
            btLayout = (LinearLayout) view.findViewById(R.id.layout_bt);
        }

    }

    public OrderGoodAdapter(List<GoodBean> mList, OrderActivity context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_order_good, parent, false);
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
                holder.btLayout.setVisibility(View.GONE);
                break;
            case "2":
                holder.statusTipTxt.setText("已取消");
                holder.btLayout.setVisibility(View.GONE);
                break;
            case "3":
                holder.statusTipTxt.setText("待发货");
                holder.bt1.setVisibility(View.INVISIBLE);
                holder.bt2.setVisibility(View.INVISIBLE);
                holder.bt3.setVisibility(View.INVISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt4.setText("退款");
                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToRefundActivity(mList.get(position).getYUYUECARD_ID());
                    }
                });
                break;
            case "4":
                holder.statusTipTxt.setText("已发货");
                holder.bt1.setVisibility(View.INVISIBLE);
                holder.bt2.setVisibility(View.VISIBLE);
                holder.bt3.setVisibility(View.VISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt2.setText("确认收货");
                holder.bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.sureOrder(mList.get(position).getYUYUECARD_ID());
                    }
                });
                holder.bt3.setText("查看物流");
                holder.bt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.seeToExpress(mList.get(position).getYUYUECARD_ID());
                    }
                });
                holder.bt4.setText("退款");
                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToRefundActivity(mList.get(position).getYUYUECARD_ID());
                    }
                });
                break;
            case "5":
                holder.statusTipTxt.setText("已完成");

                if (!TextUtils.isEmpty(mList.get(position).getCAN_REFUND()) && mList.get(position).getCAN_REFUND().equals("1")) {
                    holder.bt1.setVisibility(View.INVISIBLE);
                    holder.bt2.setVisibility(View.INVISIBLE);
                    holder.bt3.setVisibility(View.INVISIBLE);
                    holder.bt4.setVisibility(View.VISIBLE);
                    holder.bt4.setText("退款");
                    holder.bt4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.sureOrder(mList.get(position).getYUYUECARD_ID());
                        }
                    });
                } else {
                    holder.btLayout.setVisibility(View.GONE);
                }
                break;
            case "6":
                holder.statusTipTxt.setText("退款中");

                holder.bt1.setVisibility(View.INVISIBLE);
                holder.bt2.setVisibility(View.INVISIBLE);
                holder.bt3.setVisibility(View.INVISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt4.setText("退款详情");
                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToRefundAActivity(mList.get(position).getYUYUECARD_ID());
                    }
                });
                break;
            case "7":
                holder.statusTipTxt.setText("退款成功");
                holder.bt1.setVisibility(View.INVISIBLE);
                holder.bt2.setVisibility(View.INVISIBLE);
                holder.bt3.setVisibility(View.INVISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt4.setText("退款详情");
                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToRefundBActivity(mList.get(position).getYUYUECARD_ID());
                    }
                });
                break;
            case "8":
                holder.statusTipTxt.setText("退款失败");
                holder.bt1.setVisibility(View.INVISIBLE);
                holder.bt2.setVisibility(View.INVISIBLE);
                holder.bt3.setVisibility(View.INVISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt4.setText("退款详情");
                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToRefundBActivity(mList.get(position).getYUYUECARD_ID());
                    }
                });
                break;
            case "9":
                holder.statusTipTxt.setText("退款申请中");
                holder.bt1.setVisibility(View.INVISIBLE);
                holder.bt2.setVisibility(View.INVISIBLE);
                holder.bt3.setVisibility(View.INVISIBLE);
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt4.setText("退款详情");
                holder.bt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.goToRefundAActivity(mList.get(position).getYUYUECARD_ID());
                    }
                });
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
