package com.babyraising.aipaperurine.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.BrandSizeSimpleBean;

import java.util.List;

public class BrandSizeAdapter extends RecyclerView.Adapter<BrandSizeAdapter.ViewHolder> {

    private List<BrandSizeSimpleBean> mList;
    private int currentPosition = 0;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rbSize;
        TextView tvSec;

        public ViewHolder(View view) {
            super(view);
            rbSize = (TextView) view.findViewById(R.id.bt1_size);
            tvSec = (TextView) view.findViewById(R.id.tv_sec);
        }

    }

    public BrandSizeAdapter(List<BrandSizeSimpleBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_pick_size, parent, false);
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

        holder.rbSize.setText(mList.get(position).getBRAND_SIZE());
        holder.tvSec.setText(mList.get(position).getBRAND_SIZE_DESC());

        if (position == currentPosition) {
            holder.rbSize.setBackgroundResource(R.drawable.shape_pick_size_selected);
            holder.rbSize.setTextColor(Color.WHITE);
        } else {
            holder.rbSize.setBackgroundResource(R.drawable.shape_pick_size_normal);
            holder.rbSize.setTextColor(Color.GRAY);
        }

//        holder.rbSize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentPosition = position;
//            }
//        });
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
