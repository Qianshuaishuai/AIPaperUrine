package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.MessageBean;

import java.util.List;

public class OfficeMessageAdapter extends RecyclerView.Adapter<OfficeMessageAdapter.ViewHolder> {

    private List<MessageBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipTxt, timeTxt;

        public ViewHolder(View view) {
            super(view);
            tipTxt = (TextView) view.findViewById(R.id.tip);
            timeTxt = (TextView) view.findViewById(R.id.time);
        }

    }

    public OfficeMessageAdapter(List<MessageBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_office_message, parent, false);
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
        holder.timeTxt.setText(mList.get(position).getCREATETIME());
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
