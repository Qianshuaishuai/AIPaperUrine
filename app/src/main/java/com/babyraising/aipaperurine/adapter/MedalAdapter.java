package com.babyraising.aipaperurine.adapter;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.CouponMoreBean;

import java.util.List;

public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.ViewHolder> {

    private List<CouponMoreBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipTxt;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            tipTxt = (TextView) view.findViewById(R.id.tip);
            icon = (ImageView) view.findViewById(R.id.icon);
        }

    }

    public MedalAdapter(List<CouponMoreBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_medal, parent, false);
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
