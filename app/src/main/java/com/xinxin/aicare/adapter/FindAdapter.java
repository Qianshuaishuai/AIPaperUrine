package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.InformationListBean;

import org.xutils.x;

import java.util.List;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private List<InformationListBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mainTxt, subtitleTxt;
        TextView tv_sc_count, tv_dz_count, read_count;
        ImageView icon, iv_sc, iv_dz;

        public ViewHolder(View view) {
            super(view);
            mainTxt = (TextView) view.findViewById(R.id.main);
            subtitleTxt = (TextView) view.findViewById(R.id.subtitle);
            tv_sc_count = (TextView) view.findViewById(R.id.tv_sc_count);
            tv_dz_count = (TextView) view.findViewById(R.id.tv_dz_count);
            read_count = (TextView) view.findViewById(R.id.read_count);
            icon = (ImageView) view.findViewById(R.id.icon);
            iv_sc = (ImageView) view.findViewById(R.id.iv_sc);
            iv_dz = (ImageView) view.findViewById(R.id.iv_dz);
        }

    }

    public FindAdapter(List<InformationListBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_find, parent, false);
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
        holder.mainTxt.setText(mList.get(position).getTITLE());
        holder.subtitleTxt.setText(mList.get(position).getINFO());
        holder.tv_sc_count.setText(mList.get(position).getCOLLECTNUM());
        holder.tv_dz_count.setText(mList.get(position).getSTARNUM());
        holder.read_count.setText(mList.get(position).getREADNUM() + "人阅读");

        if (mList.get(position).getIS_STAR() == "1") {
            holder.iv_dz.setImageResource(R.mipmap.btn_dianzan_light);
        } else {
            holder.iv_dz.setImageResource(R.mipmap.btn_dianzan_nor);
        }

        if (mList.get(position).getIS_COLLECT() == "1") {
            holder.iv_sc.setImageResource(R.mipmap.btn_shoucang_light);
        } else {
            holder.iv_sc.setImageResource(R.mipmap.btn_shoucang_nor);
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
