package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.MemberDataCal4Bean;
import com.xinxin.aicare.ui.info.UrineDetailActivity;

import java.util.List;

public class UrineRecordAdapter extends RecyclerView.Adapter<UrineRecordAdapter.ViewHolder> {

    private List<MemberDataCal4Bean> mList;
    private UrineDetailActivity context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTxt;
        Button btMore;
        ImageView iv_star1, iv_star2, iv_star3;

        public ViewHolder(View view) {
            super(view);
            timeTxt = (TextView) view.findViewById(R.id.time);
            btMore = (Button) view.findViewById(R.id.bt_more);
            iv_star1 = (ImageView) view.findViewById(R.id.iv_star1);
            iv_star2 = (ImageView) view.findViewById(R.id.iv_star2);
            iv_star3 = (ImageView) view.findViewById(R.id.iv_star3);
        }

    }

    public UrineRecordAdapter(UrineDetailActivity context, List<MemberDataCal4Bean> mList) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_urine_record, parent, false);
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

        holder.timeTxt.setText(mList.get(position).getDOWNTIME());

        switch (mList.get(position).getSTAR()) {
            case "0":
                holder.iv_star1.setVisibility(View.GONE);
                holder.iv_star2.setVisibility(View.GONE);
                holder.iv_star3.setVisibility(View.GONE);
                break;
            case "1":
                holder.iv_star1.setVisibility(View.VISIBLE);
                holder.iv_star2.setVisibility(View.GONE);
                holder.iv_star3.setVisibility(View.GONE);
                break;
            case "2":
                holder.iv_star1.setVisibility(View.VISIBLE);
                holder.iv_star2.setVisibility(View.VISIBLE);
                holder.iv_star3.setVisibility(View.GONE);
                break;
            case "3":
                holder.iv_star1.setVisibility(View.VISIBLE);
                holder.iv_star2.setVisibility(View.VISIBLE);
                holder.iv_star3.setVisibility(View.VISIBLE);
                break;
        }

        holder.btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToUrineMoreActivity(mList.get(position).getDEVICEDATA_ID());
            }
        });
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
