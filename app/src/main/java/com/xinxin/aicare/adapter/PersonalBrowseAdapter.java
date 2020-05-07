package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.PersonalActBean;

import org.xutils.x;

import java.util.List;

public class PersonalBrowseAdapter extends RecyclerView.Adapter<PersonalBrowseAdapter.ViewHolder> {

    private List<PersonalActBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, subtitleTxt, timeTxt;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            timeTxt = (TextView) view.findViewById(R.id.time);
            subtitleTxt = (TextView) view.findViewById(R.id.subtitle);
            titleTxt = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
        }

    }

    public PersonalBrowseAdapter(List<PersonalActBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_person_browse, parent, false);
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

        x.image().bind(holder.icon, mList.get(position).getPIC());
        holder.titleTxt.setText(mList.get(position).getTITLE());
        holder.subtitleTxt.setText(mList.get(position).getINFO());
        holder.timeTxt.setText(mList.get(position).getPUBLISHTIME());
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
