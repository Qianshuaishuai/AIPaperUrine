package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.MedalSimpleBean;

import java.util.List;

public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.ViewHolder> {

    private List<MedalSimpleBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipTxt;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            tipTxt = (TextView) view.findViewById(R.id.tip);
            icon = (ImageView) view.findViewById(R.id.icon);
        }

    }

    public MedalAdapter(List<MedalSimpleBean> mList) {
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
        holder.icon.setImageResource(mList.get(position).getPIC());
        holder.tipTxt.setText(mList.get(position).getTAG());
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
