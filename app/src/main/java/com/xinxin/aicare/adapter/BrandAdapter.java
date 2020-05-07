package com.xinxin.aicare.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xinxin.aicare.R;
import com.xinxin.aicare.ui.info.PickSizeActivity;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private List<String> mList;
    private int currentPosition = 0;
    private PickSizeActivity context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        Button rbTxt;

        public ViewHolder(View view) {
            super(view);
            rbTxt = (Button) view.findViewById(R.id.bt_brand);
        }

    }

    public BrandAdapter(List<String> mList,PickSizeActivity context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_brand, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
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

        holder.rbTxt.setText(mList.get(position));
        holder.rbTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                context.changeBrand(position);
            }
        });

        if (position == currentPosition) {
            holder.rbTxt.setBackgroundResource(R.drawable.shape_bottom_pick_brand_selected);
            holder.rbTxt.setTextColor(Color.WHITE);
        } else {
            holder.rbTxt.setBackgroundResource(R.drawable.shape_bottom_pick_brand_normal);
            holder.rbTxt.setTextColor(Color.GRAY);
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
