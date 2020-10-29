package com.xinxin.aicare.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.bean.BluetoothReceiveBean;
import com.xinxin.aicare.bean.MemberDeviceParamListBean;
import com.xinxin.aicare.bean.MemberListBean;
import com.xinxin.aicare.response.MemberDeviceParamListResponse;
import com.xinxin.aicare.ui.main.HomeFragment;
import com.xinxin.aicare.util.NumberUtil;
import com.xinxin.aicare.util.T;
import com.xinxin.aicare.util.UrineUtil;
import com.xinxin.aicare.view.HalfCircleProgressView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<MemberListBean> mList;
    private HomeFragment context;
    private BluetoothReceiveBean bluetoothReceiveBean;
    private List<MemberDeviceParamListBean> deviceParamList;
    private int isConnect = 0;
    private int iSConnectStatus = 0;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon, cardMessage, cardSetting, cardData;
        ImageView mainTipIv, postureIv, brandLogo, connectIcon;

        TextView cardName, cardTime, cardMessageCount, postureTv;
        TextView mainTipTv, mainPercentTv, mainTempTv, mainSleepTv, mainSize, mainBrand;

        HalfCircleProgressView cpv;
        LinearLayout posLayout, sizeLayout, cardLayout, postureLayout, babyLayout;

        public ViewHolder(View view) {
            super(view);
            cardIcon = (ImageView) view.findViewById(R.id.card_icon);
            connectIcon = (ImageView) view.findViewById(R.id.connect_icon);
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
//            mainBeamTv = (TextView) view.findViewById(R.id.main_beam_tv);
            mainSleepTv = (TextView) view.findViewById(R.id.main_sleep_tv);
            mainSize = (TextView) view.findViewById(R.id.main_size);
            posLayout = (LinearLayout) view.findViewById(R.id.layout_posture);
            cardLayout = (LinearLayout) view.findViewById(R.id.card_layout);
            sizeLayout = (LinearLayout) view.findViewById(R.id.layout_size);
            postureLayout = (LinearLayout) view.findViewById(R.id.layout_posture);
            mainBrand = (TextView) view.findViewById(R.id.main_brand);
            cpv = (HalfCircleProgressView) view.findViewById(R.id.cpv);
            babyLayout = (LinearLayout) view.findViewById(R.id.layout_baby);
        }

    }

    public MemberAdapter(List<MemberListBean> mList, HomeFragment context) {
        this.mList = mList;
        this.context = context;
    }

    public void setBluetoothReceiveBean(BluetoothReceiveBean bean) {
        this.bluetoothReceiveBean = bean;
    }

    public void setIsConnect(int isConnect) {
        this.isConnect = isConnect;
    }

    public void setIsConnectStatus(int isConnectStatus) {
        this.iSConnectStatus = isConnectStatus;
    }

    public void setMemberDeviceParamListBean(List<MemberDeviceParamListBean> deviceParamList) {
        this.deviceParamList = deviceParamList;

        for (int m = 0; m < mList.size(); m++) {
            for (int d = 0; d < deviceParamList.size(); d++) {
                if (mList.get(m).getDEVICE_CODE().equals(deviceParamList.get(d).getDEVICE_CODE())) {
                    mList.get(m).setParamListBean(deviceParamList.get(d));
                }
            }
        }
    }

    public void setMemberList(List<MemberListBean> memberList) {
        mList.clear();
        for (int m = 0; m < memberList.size(); m++) {
            mList.add(memberList.get(m));
        }
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

        translateBlueToothData();

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
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
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
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
                context.goToMemberMessage(mList.get(position).getMEMBER_ID());
            }
        });

        holder.cardSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
                context.goToMemberSetting(mList.get(position).getMEMBER_ID(), mList.get(position).getDEVICE_CODE());
            }
        });

        holder.cardData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
                context.goToUrineDetailActivity(mList.get(position).getMEMBER_ID());
            }
        });

        if (TextUtils.isEmpty(mList.get(position).getTEMPERATURE())) {
            holder.mainTempTv.setText("--℃");
        } else {
            holder.mainTempTv.setText(mList.get(position).getTEMPERATURE() + "℃");
        }

//        if (TextUtils.isEmpty(mList.get(position).getURINE_VOLUME())) {
//            holder.mainBeamTv.setText("--ml");
//        } else {
//            holder.mainBeamTv.setText(mList.get(position).getURINE_VOLUME() + "ml");
//        }

        if (TextUtils.isEmpty(mList.get(position).getURINE_VOLUME_PERCENT())) {
            holder.mainPercentTv.setText("--%");
        } else {
            holder.mainPercentTv.setText(mList.get(position).getURINE_VOLUME_PERCENT() + "%");
        }

        holder.mainPercentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
                if (TextUtils.isEmpty(mList.get(position).getURINE_VOLUME())) {
                    T.s("当前尿量为--ml");
                } else {
                    T.s("当前尿量为" + mList.get(position).getURINE_VOLUME() + "ml");
                }
            }
        });

        if (TextUtils.isEmpty(mList.get(position).getSLEEP_TIME())) {
            holder.mainSleepTv.setText("0.0min");
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
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
                context.goToPickSizeActivity(mList.get(position).getMEMBER_ID(), mList.get(position).getDIAPER_BRAND(), mList.get(position).getDIAPER_SIZE());
            }
        });
//        System.out.println(mList.get(position).getSLEEP_POSTURE());

        holder.mainBrand.setText(mList.get(position).getBRAND_NAME());

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.goToBabyInfoSetting(mList.get(position));
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
            }
        });

        holder.postureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
                switch (mList.get(position).getSLEEP_POSTURE()) {
                    case "1":
                        T.s(Constant.SLEEP_POS_TIPS[3]);
                        break;
                    case "2":
                        T.s(Constant.SLEEP_POS_TIPS[2]);
                        break;
                    case "4":
                        T.s(Constant.SLEEP_POS_TIPS[0]);
                        break;
                    case "8":
                        T.s(Constant.SLEEP_POS_TIPS[1]);
                        break;

                    default:
                        T.s("当前未获取到宝宝睡姿");
                        break;
                }


            }
        });

        if (TextUtils.isEmpty(mList.get(position).getSLEEP_POSTURE())) {
            holder.postureTv.setVisibility(View.VISIBLE);
            holder.postureIv.setVisibility(View.GONE);
        } else {
            holder.postureTv.setVisibility(View.GONE);
            holder.postureIv.setVisibility(View.VISIBLE);
            switch (mList.get(position).getSLEEP_POSTURE()) {
                case "1":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaopashui_da);
//                    holder.postureTv.setText("宝宝趴睡");
                    break;
                case "2":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaoyoutang_da);
//                    holder.postureTv.setText("宝宝右躺");
                    break;
                case "4":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaoyangshui_da);
//                    holder.postureTv.setText("宝宝仰睡");
                    break;
                case "8":
                    holder.postureIv.setImageResource(R.mipmap.img_baobaozuotang_da);
//                    holder.postureTv.setText("宝宝左躺");
                    break;
            }
        }


        holder.cardTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cardTime.getText().toString().equals("设备未绑定")) {
                    context.goToDeviceConnect(mList.get(position).getMEMBER_ID());
                } else {
                    T.s("设备已绑定");
//                    context.goToDeviceConnect(mList.get(position).getMEMBER_ID());
                }
            }
        });

        if (TextUtils.isEmpty(mList.get(position).getDEVICE_CODE())) {
            holder.cardTime.setText("设备未绑定");
            holder.cardTime.setTextColor(ContextCompat.getColor(context.getActivity(), R.color.connect_none));
        } else {
            if (isConnect == 1) {
                holder.cardTime.setText("接收中");
                holder.cardTime.setTextColor(ContextCompat.getColor(context.getActivity(), R.color.connect_success));
            } else {
                holder.cardTime.setText("未连接");
                holder.cardTime.setTextColor(ContextCompat.getColor(context.getActivity(), R.color.connect_default));
            }
        }

        if (holder.cardTime.getText().toString().equals("设备未绑定")) {
            holder.connectIcon.setImageResource(R.mipmap.btn_lanya);
        } else {
            if (iSConnectStatus == 1) {
                holder.connectIcon.setImageResource(R.mipmap.btn_lanya_clicked);
            } else {
                holder.connectIcon.setImageResource(R.mipmap.btn_lanya);
            }
        }

        holder.babyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.isShowIntroduce) {
                    context.showIntroduceLayout();
                    return;
                }
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

    //本地获取解析后数据
    private void translateBlueToothData() {
        if (bluetoothReceiveBean != null) {
            for (int m = 0; m < mList.size(); m++) {
                if (mList.get(m).getDEVICE_CODE().equals(bluetoothReceiveBean.getDEVICE_ID()) && mList.get(m).getParamListBean() != null) {
                    //随时存储本地解析蓝牙数据
                    ((PaperUrineApplication) context.getActivity().getApplication()).saveNewestBluetoothReceiveBean(bluetoothReceiveBean);
                    if (bluetoothReceiveBean.getD0().equals("0")) {
                        mList.get(m).setURINE_VOLUME("");
                        mList.get(m).setURINE_VOLUME_PERCENT("");
                        mList.get(m).setSLEEP_POSTURE("");
                        mList.get(m).setTEMPERATURE("");
                    } else if (bluetoothReceiveBean.getD0().equals("1")) {
                        //处理睡姿
                        mList.get(m).setSLEEP_POSTURE(bluetoothReceiveBean.getD5());
                        //处理尿湿
                        boolean is_URINE_VOLUME_F = false;
                        if (!TextUtils.isEmpty(bluetoothReceiveBean.getAD())) {
                            String S = mList.get(m).getParamListBean().getNS_S();//阈值，用户设置的尿湿节点
                            String F = mList.get(m).getParamListBean().getNS_F();//阈值，用户设置的尿满节点
                            String baseVol = NumberUtil.div(NumberUtil.mul(mList.get(m).getParamListBean().getWATER_HOLDING_VALUE(), mList.get(m).getParamListBean().getALARM_LIMIT_VALUE()), 100) + "";//80
                            double doubleA = NumberUtil.div(NumberUtil.mul(F, baseVol), 100); //A=F*V*P,A值精确到10位   S 40  F 50
                            doubleA = NumberUtil.div(doubleA, 10, 0) * 10;

                            int URINE_VOLUME_temp = UrineUtil.getUrineVolume(bluetoothReceiveBean.getAD(), mList.get(m).getParamListBean().getTHERMOMETER(), mList.get(m).getParamListBean().getNUMERICAL_TABLE());
                            String URINE_VOLUME = (URINE_VOLUME_temp >= 0 ? URINE_VOLUME_temp + "" : ">" + (URINE_VOLUME_temp * -1));//尿量，单位ml  -----页面显示的尿量
                            double URINE_VOLUME_PERCENT_double = (URINE_VOLUME_temp >= 0 ? (NumberUtil.mul(NumberUtil.div(URINE_VOLUME_temp, doubleA), 100)) : (NumberUtil.mul(NumberUtil.div(URINE_VOLUME_temp * -1, doubleA), 100)));
                            String URINE_VOLUME_PERCENT = URINE_VOLUME_PERCENT_double + "";//		  -----页面显示的尿量比例
                            double doubleS = NumberUtil.div(NumberUtil.mul(S, baseVol), 100);
                            boolean is_URINE_VOLUME_S = (URINE_VOLUME_temp >= 0 ? NumberUtil.sub(URINE_VOLUME_temp, doubleS) >= 0 : true);//是否尿湿  ----是否触发尿湿值。
                            is_URINE_VOLUME_F = (URINE_VOLUME_PERCENT_double >= 100 ? true : false);//是否尿满		----是否触发尿满值。
                            //尿湿百分比
                            mList.get(m).setURINE_VOLUME(URINE_VOLUME);
                            mList.get(m).setURINE_VOLUME_PERCENT(URINE_VOLUME_PERCENT);

                        }
                        //处理温度
                        int currentTemp = 0;
                        if (bluetoothReceiveBean.getD4().equals("0xFF")) {
                            mList.get(m).setTEMPERATURE(">50");
                        } else if (bluetoothReceiveBean.getD4().equals("0xFE")) {
                            mList.get(m).setTEMPERATURE("<0");
                        } else {
                            if (!TextUtils.isEmpty(bluetoothReceiveBean.getD4())) {
                                currentTemp = Integer.parseInt(bluetoothReceiveBean.getD4());
                                mList.get(m).setTEMPERATURE(String.valueOf(currentTemp * 0.5));
                            }
                        }

                        //睡眠时间--暂时不用
//                        if (!TextUtils.isEmpty(bluetoothReceiveBean.getD6())) {
//                            int sleepTime = Integer.parseInt(bluetoothReceiveBean.getD6());
//                            double f1 = new BigDecimal((float) sleepTime / 60).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                            mList.get(m).setSLEEP_TIME(String.valueOf(f1));
//                        }

                        //是否进行报警通知提示，0尿湿提醒, 1尿满提醒, 2过冷提醒, 3过热提醒, 4趴睡提醒
                        int tipNumber = -1;
                        if (bluetoothReceiveBean.getD5().equals("1") && mList.get(m).getParamListBean().getPS_SWITH().equals("1")) {
                            tipNumber = 4;
                        }

                        //温度判断--过冷
                        double TB_TEMP = 0;
                        String TB_SWITH = mList.get(m).getParamListBean().getTB_SWITH();
                        if (!TextUtils.isEmpty(mList.get(m).getParamListBean().getTB_TEMPERATURE())) {
                            TB_TEMP = Double.parseDouble(mList.get(m).getParamListBean().getTB_TEMPERATURE());
                        }

                        if (TB_SWITH.equals("1")) {
                            if (bluetoothReceiveBean.getD4().equals("0xFF")) {
                                tipNumber = 2;
                            }

                            if (currentTemp * 0.5 < TB_TEMP) {
                                tipNumber = 2;
                            }
                        }

                        //温度判断--过热
                        double FZX_TEMP = 0;
                        String FZX_SWITH = mList.get(m).getParamListBean().getFZX_SWITH();
                        if (!TextUtils.isEmpty(mList.get(m).getParamListBean().getFZX_TEMPERATURE())) {
                            FZX_TEMP = Double.parseDouble(mList.get(m).getParamListBean().getFZX_TEMPERATURE());
                        }

                        if (FZX_SWITH.equals("1")) {
                            if (bluetoothReceiveBean.getD4().equals("0xFE")) {
                                tipNumber = 3;
                            }

                            if (currentTemp * 0.5 > FZX_TEMP) {
                                tipNumber = 3;
                            }
                        }

                        //尿满
                        String NS_SWITH = mList.get(m).getParamListBean().getNS_SWITH();
                        if (NS_SWITH.equals("1")) {
                            if (is_URINE_VOLUME_F) {
                                tipNumber = 1;
                            }
                        }

                        if (tipNumber != -1) {
                            String title = Constant.WARNING_TITLES[tipNumber];
                            String tip = Constant.WARNING_TIPS[tipNumber];
                            tip = tip.replace("NICKNAME", mList.get(m).getNICKNAME());
                            context.showTipDialog(title, tip, tipNumber);
                        }
                    }
                }
            }
        } else {
            //初始化
            for (int m = 0; m < mList.size(); m++) {
                mList.get(m).setURINE_VOLUME("");
                mList.get(m).setURINE_VOLUME_PERCENT("");
                mList.get(m).setSLEEP_POSTURE("");
                mList.get(m).setTEMPERATURE("");
                mList.get(m).setSLEEP_TIME("");
            }
        }
    }
}
