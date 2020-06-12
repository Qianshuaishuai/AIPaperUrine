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
import com.xinxin.aicare.bean.MemberListBean;
import com.xinxin.aicare.ui.main.HomeFragment;
import com.xinxin.aicare.util.T;
import com.xinxin.aicare.view.HalfCircleProgressView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<MemberListBean> mList;
    private HomeFragment context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon, cardMessage, cardSetting, cardData;
        ImageView mainTipIv, postureIv, brandLogo;

        TextView cardName, cardTime, cardMessageCount, postureTv;
        TextView mainTipTv, mainPercentTv, mainTempTv, mainBeamTv, mainSleepTv, mainSize, mainBrand;

        HalfCircleProgressView cpv;
        LinearLayout posLayout, sizeLayout, cardLayout, postureLayout;

        public ViewHolder(View view) {
            super(view);
            cardIcon = (ImageView) view.findViewById(R.id.card_icon);
            postureIv = (ImageView) view.findViewById(R.id.posture_iv);
            postureTv = (TextView) view.findViewById(R.id.posture_tv);
            cardMessage = (ImageView) view.findViewById(R.id.card_message);
            cardSetting = (ImageView) view.findViewById(R.id.card_setting);
            mainTipIv = (ImageView) view.findViewById(R.id.main_tip_icon);
            cardData = (ImageView) view.findViewById(R.id.card_data);
            brandLogo = (ImageView) view.findViewById(R.id.brand_logo);
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
            cardLayout = (LinearLayout) view.findViewById(R.id.card_layout);
            sizeLayout = (LinearLayout) view.findViewById(R.id.layout_size);
            postureLayout = (LinearLayout) view.findViewById(R.id.layout_posture);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

        holder.posLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!TextUtils.isEmpty(mList.get(position).getSLEEP_POSTURE())) {
//                    context.goToPostureActivity(mList.get(position).getSLEEP_POSTURE());
//                } else {
//                    T.s("请先绑定设备!");
//                }
            }
        });

        ImageOptions options = new ImageOptions.Builder().
                setRadius(DensityUtil.dip2px(66)).setCrop(true).build();
        x.image().bind(holder.cardIcon, mList.get(position).getHEADIMG(), options);
        x.image().bind(holder.brandLogo, mList.get(position).getBRAND_LOGO());
//        System.out.println(mList.get(position).getHAS_WAITREAD());
        holder.cardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToBabyInfoSetting(mList.get(position));
            }
        });
        holder.cardName.setText(mList.get(position).getNICKNAME());
        holder.cardMessageCount.setText(mList.get(position).getHAS_WAITREAD());
//        System.out.println();
        if (!TextUtils.isEmpty(mList.get(position).getHAS_WAITREAD()) && Integer.parseInt(mList.get(position).getHAS_WAITREAD()) >= 100) {
            holder.cardMessageCount.setText("99+");
        }
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
                context.goToMemberSetting(mList.get(position).getMEMBER_ID(), mList.get(position).getDEVICE_CODE());
            }
        });

        holder.cardData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToUrineDetailActivity(mList.get(position).getMEMBER_ID());
            }
        });

        holder.cardTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cardTime.getText().toString().equals("点击绑定设备")) {
                    context.goToDeviceConnect(mList.get(position).getMEMBER_ID());
                } else {
                    T.s("设备已绑定");
//                    context.goToDeviceConnect(mList.get(position).getMEMBER_ID());
                }
            }
        });

        if (TextUtils.isEmpty(mList.get(position).getDEVICE_CODE())) {
            holder.cardTime.setText("点击绑定设备");
        } else {
            holder.cardTime.setText("已绑定设备");
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

        double percent = 0.0;
        if (!TextUtils.isEmpty(mList.get(position).getURINE_VOLUME_PERCENT())) {
            percent = Double.parseDouble(mList.get(position).getURINE_VOLUME_PERCENT());
        }
        holder.cpv.setValue(0);
        holder.cpv.setProgress(new Double(180 * percent / 100).intValue());

        if (percent >= 100) {
            holder.mainTipIv.setVisibility(View.VISIBLE);
            holder.mainTipTv.setVisibility(View.VISIBLE);
        } else {
            holder.mainTipIv.setVisibility(View.GONE);
            holder.mainTipTv.setVisibility(View.GONE);
        }

        holder.sizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToPickSizeActivity(mList.get(position).getMEMBER_ID());
            }
        });
//        System.out.println(mList.get(position).getSLEEP_POSTURE());

        holder.mainBrand.setText(mList.get(position).getBRAND_NAME());

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.goToBabyInfoSetting(mList.get(position));
            }
        });

        if (TextUtils.isEmpty(mList.get(position).getSLEEP_POSTURE())) {

        } else {
            switch (mList.get(position).getSLEEP_POSTURE()) {
                case "1":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaopashui_da);
                    holder.postureTv.setText("宝宝趴睡");
                    break;
                case "2":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaoyoutang_da);
                    holder.postureTv.setText("宝宝右躺");
                    break;
                case "4":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaoyangshui_da);
                    holder.postureTv.setText("宝宝仰睡");
                    break;
                case "8":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaozuotang_da);
                    holder.postureTv.setText("宝宝左躺");
                    break;
            }
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
