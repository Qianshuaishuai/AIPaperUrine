package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.GrowthPointBean;

import java.util.List;

public class GrowthAdapter extends RecyclerView.Adapter<GrowthAdapter.ViewHolder> {

    private List<GrowthPointBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, timeTxt, countTxt;

        public ViewHolder(View view) {
            super(view);
            titleTxt = (TextView) view.findViewById(R.id.title);
            timeTxt = (TextView) view.findViewById(R.id.time);
            countTxt = (TextView) view.findViewById(R.id.count);
        }

    }

    public GrowthAdapter(List<GrowthPointBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_growth, parent, false);
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

        holder.titleTxt.setText(mList.get(position).getSOURCE());
        holder.timeTxt.setText(mList.get(position).getCREATETIME());

        if (mList.get(position).getTYPE().equals("1")) {
            holder.countTxt.setText("+" + mList.get(position).getVALUE());
        } else if (mList.get(position).getTYPE().equals("2")) {
            holder.countTxt.setText("-" + mList.get(position).getVALUE());
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
