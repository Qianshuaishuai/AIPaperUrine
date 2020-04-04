package com.babyraising.aipaperurine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.AddressBean;
import com.babyraising.aipaperurine.bean.MemberListBean;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<MemberListBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon, cardMessage, cardSetting;

        TextView cardName, cardTime;

        public ViewHolder(View view) {
            super(view);
            cardIcon = (ImageView) view.findViewById(R.id.card_icon);
            cardMessage = (ImageView) view.findViewById(R.id.card_message);
            cardSetting = (ImageView) view.findViewById(R.id.card_setting);
            cardName = (TextView) view.findViewById(R.id.card_name);
            cardTime = (TextView) view.findViewById(R.id.card_time);
        }

    }

    public MemberAdapter(List<MemberListBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_baby, parent, false);
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
        ImageOptions options = new ImageOptions.Builder().
                setRadius(DensityUtil.dip2px(66)).build();
        x.image().bind(holder.cardIcon, mList.get(position).getHEADIMG(), options);

        holder.cardName.setText(mList.get(position).getNICKNAME());
        holder.cardTime.setText(mList.get(position).getCREATETIME());

        holder.cardMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.cardSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
