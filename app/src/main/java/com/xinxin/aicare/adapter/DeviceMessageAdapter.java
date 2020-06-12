package com.xinxin.aicare.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.MessageBean;

import java.util.List;

public class DeviceMessageAdapter extends RecyclerView.Adapter<DeviceMessageAdapter.ViewHolder> {

    private List<MessageBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipTxt, timeTxt, nameTxt;

        public ViewHolder(View view) {
            super(view);
            tipTxt = (TextView) view.findViewById(R.id.tip);
            timeTxt = (TextView) view.findViewById(R.id.time);
            nameTxt = (TextView) view.findViewById(R.id.name);
        }

    }

    public DeviceMessageAdapter(List<MessageBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_device_message, parent, false);
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

        holder.tipTxt.setText(mList.get(position).getTITLE());
        holder.nameTxt.setText(mList.get(position).getMEMBER_NAME());
        holder.timeTxt.setText(mList.get(position).getCREATETIME());

        if(mList.get(position).getSTATE().equals("1")){
            holder.tipTxt.setTextColor(Color.parseColor("#1E1E1E"));
        }else if(mList.get(position).getSTATE().equals("3")){
            holder.tipTxt.setTextColor(Color.parseColor("#999999"));
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
