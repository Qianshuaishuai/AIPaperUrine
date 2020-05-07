package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.WeekGrowthRankingBean;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

public class RankWeekAdapter extends RecyclerView.Adapter<RankWeekAdapter.ViewHolder> {

    private List<WeekGrowthRankingBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutType1, layoutType2;
        TextView rankNumTxt, nameTxt, meRankNumTxt, tipTxt, meName;
        ImageView icon, meIcon;

        public ViewHolder(View view) {
            super(view);
            layoutType1 = (LinearLayout) view.findViewById(R.id.layout_type1);
            layoutType2 = (LinearLayout) view.findViewById(R.id.layout_type2);
            rankNumTxt = (TextView) view.findViewById(R.id.rank_num);
            nameTxt = (TextView) view.findViewById(R.id.name);
            meRankNumTxt = (TextView) view.findViewById(R.id.me_rank_num);
            tipTxt = (TextView) view.findViewById(R.id.tip);
            meName = (TextView) view.findViewById(R.id.me_name);
            icon = (ImageView) view.findViewById(R.id.icon);
            meIcon = (ImageView) view.findViewById(R.id.me_icon);
        }

    }

    public RankWeekAdapter(List<WeekGrowthRankingBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_rank_grow, parent, false);
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

        if (position == mList.size() - 1) {
            holder.layoutType2.setVisibility(View.VISIBLE);
            holder.meRankNumTxt.setText("我的排" + mList.get(position).getAPPUSER_ID() + "名");
            if (TextUtils.isEmpty(mList.get(position).getNICKNAME())) {
                holder.meName.setVisibility(View.GONE);
                holder.tipTxt.setVisibility(View.VISIBLE);
            } else {
                holder.meName.setVisibility(View.VISIBLE);
                holder.tipTxt.setVisibility(View.GONE);
                holder.meName.setText(mList.get(position).getNICKNAME());
            }
            ImageOptions options = new ImageOptions.Builder().
                    setRadius(DensityUtil.dip2px(45)).setCrop(true).build();
            x.image().bind(holder.meIcon, mList.get(position).getHEADIMG(), options);
            if (TextUtils.isEmpty(mList.get(position).getAPPUSER_ID())){
                holder.meRankNumTxt.setText("我的排" + "/" + "名");
            }
        } else {
            holder.layoutType1.setVisibility(View.VISIBLE);
            ImageOptions options = new ImageOptions.Builder().
                    setRadius(DensityUtil.dip2px(45)).setCrop(true).build();
            x.image().bind(holder.icon, mList.get(position).getHEADIMG(), options);
            holder.nameTxt.setText(mList.get(position).getNICKNAME());
            holder.rankNumTxt.setText("" + (position+1));
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
