package com.babyraising.aipaperurine.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.AddressBean;
import com.babyraising.aipaperurine.bean.MemberListBean;
import com.babyraising.aipaperurine.ui.main.HomeFragment;
import com.babyraising.aipaperurine.view.HalfCircleProgressView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<MemberListBean> mList;
    private HomeFragment context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon, cardMessage, cardSetting, cardData;
        ImageView mainTipIv;

        TextView cardName, cardTime, cardMessageCount;
        TextView mainTipTv, mainPercentTv, mainTempTv, mainBeamTv, mainSleepTv, mainSize, mainBrand;

        HalfCircleProgressView cpv;
        LinearLayout posLayout, sizeLayout;

        public ViewHolder(View view) {
            super(view);
            cardIcon = (ImageView) view.findViewById(R.id.card_icon);
            cardMessage = (ImageView) view.findViewById(R.id.card_message);
            cardSetting = (ImageView) view.findViewById(R.id.card_setting);
            mainTipIv = (ImageView) view.findViewById(R.id.main_tip_icon);
            cardData = (ImageView) view.findViewById(R.id.card_data);
            cardName = (TextView) view.findViewById(R.id.card_name);
            cardTime = (TextView) view.findViewById(R.id.card_time);
            cardMessageCount = (TextView) view.findViewById(R.id.card_message_count);
            mainTipTv = (TextView) view.findViewById(R.id.main_tip_tv);
            mainPercentTv = (TextView) view.findViewById(R.id.main_percent_tv);
            mainTempTv = (TextView) view.findViewById(R.id.main_temp_tv);
            mainBeamTv = (TextView) view.findViewById(R.id.main_beam_tv);
            mainSleepTv = (TextView) view.findViewById(R.id.main_sleep_tv);
            mainSize = (TextView) view.findViewById(R.id.main_size);
            posLayout = (LinearLayout) view.findViewById(R.id.layout_posture);
            sizeLayout = (LinearLayout) view.findViewById(R.id.layout_size);
            mainBrand = (TextView) view.findViewById(R.id.main_brand);
            cpv = (HalfCircleProgressView) view.findViewById(R.id.cpv);
        }

    }

    public MemberAdapter(List<MemberListBean> mList, HomeFragment context) {
        this.mList = mList;
        this.context = context;
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
//        System.out.println(mList.get(position).getHAS_WAITREAD());
        holder.cardName.setText(mList.get(position).getNICKNAME());
        holder.cardMessageCount.setText(mList.get(position).getHAS_WAITREAD());
//        holder.mainSleepTv.setText(mList.get(position).getSLEEP_TIME());
        holder.mainSize.setText(mList.get(position).getDIAPER_SIZE());

        holder.cardMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToMemberMessage(mList.get(position).getMEMBER_ID());
            }
        });

        holder.cardSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToMemberSetting(mList.get(position).getMEMBER_ID());
            }
        });

        holder.cardData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToUrineDetailActivity(mList.get(position).getMEMBER_ID());
            }
        });

        if (TextUtils.isEmpty(mList.get(position).getCREATETIME())) {
            holder.cardTime.setText("设备未链接，点击绑定");
            holder.cardTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            holder.cardTime.setText(mList.get(position).getCREATETIME());
        }


        if (TextUtils.isEmpty(mList.get(position).getTEMPERATURE())) {
            holder.mainTempTv.setText("0℃");
        } else {
            holder.mainTempTv.setText(mList.get(position).getTEMPERATURE() + "℃");
        }

        if (TextUtils.isEmpty(mList.get(position).getURINE_VOLUME())) {
            holder.mainBeamTv.setText("0ml");
        } else {
            holder.mainBeamTv.setText(mList.get(position).getURINE_VOLUME() + "ml");
        }

        if (TextUtils.isEmpty(mList.get(position).getURINE_VOLUME_PERCENT())) {
            holder.mainPercentTv.setText("0%");
        } else {
            holder.mainPercentTv.setText(mList.get(position).getURINE_VOLUME_PERCENT() + "%");
        }

        if (TextUtils.isEmpty(mList.get(position).getSLEEP_TIME())) {
            holder.mainSleepTv.setText("0min");
        } else {
            holder.mainSleepTv.setText(mList.get(position).getSLEEP_TIME() + "min");
        }

        int percent = 0;
        if (!TextUtils.isEmpty(mList.get(position).getURINE_VOLUME_PERCENT())) {
            percent = Integer.parseInt(mList.get(position).getURINE_VOLUME_PERCENT());
        }
        holder.cpv.setValue(0);
        holder.cpv.setProgress(180 * percent / 100);

        if (percent >= 100) {
            holder.mainTipIv.setVisibility(View.VISIBLE);
            holder.mainTipIv.setVisibility(View.VISIBLE);
        }

        holder.sizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToPickSizeActivity(mList.get(position).getMEMBER_ID());
            }
        });
//        System.out.println(mList.get(position).getSLEEP_POSTURE());

        holder.mainBrand.setText(mList.get(position).getBRAND_NAME());
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
