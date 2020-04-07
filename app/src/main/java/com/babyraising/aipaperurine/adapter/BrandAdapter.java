package com.babyraising.aipaperurine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.babyraising.aipaperurine.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private List<String> mList;
    private int currentPosition = 0;

    static class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbTxt;

        public ViewHolder(View view) {
            super(view);
            rbTxt = (RadioButton) view.findViewById(R.id.bt_brand);
        }

    }

    public BrandAdapter(List<String> mList) {
        this.mList = mList;
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

        if (position == currentPosition) {
            holder.rbTxt.setChecked(true);
        } else {
            holder.rbTxt.setChecked(false);
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
