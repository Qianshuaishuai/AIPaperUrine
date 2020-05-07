package com.xinxin.aicare.ui.info;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.DeviceParamInfoBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.DeviceParamInfoResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_call_setting)
public class CallSettingActivity extends BaseActivity {

    private UserBean userBean;
    private String memberId;
    private DeviceParamInfoBean bean;

    private AlertDialog deleteDialog;
    private AlertDialog rebindDialog;

    @ViewInject(R.id.ns_low)
    private TextView nsLow;

    @ViewInject(R.id.ns_height)
    private TextView nsHeight;

    @ViewInject(R.id.ns_rem)
    private TextView nsRem;

    @ViewInject(R.id.niaoxing_tv)
    private TextView nsTv;

    @ViewInject(R.id.connect_device_no)
    private TextView connectDeviceNo;

    @ViewInject(R.id.tibei_tv)
    private TextView tibeiTv;

    @ViewInject(R.id.tibei_sb)
    private SeekBar tibeiSb;

    @ViewInject(R.id.niaoxing_tv)
    private TextView niaoxingTv;

    @ViewInject(R.id.niaoxing_sb)
    private SeekBar niaoxingSb;

    @ViewInject(R.id.zhixi_tv)
    private TextView zhixiTv;

    @ViewInject(R.id.zhixi_sb)
    private SeekBar zhixiSb;

    @ViewInject(R.id.tv_switch_ns)
    private TextView tvSwitchNs;

    @ViewInject(R.id.cb_wake)
    private CheckBox wakeBox;

    @ViewInject(R.id.tv_switch_tb)
    private TextView tvSwitchTb;

    @ViewInject(R.id.cb_kick)
    private CheckBox kickBox;

    @ViewInject(R.id.tv_switch_anti)
    private TextView tvSwitchAnti;

    @ViewInject(R.id.cb_anti)
    private CheckBox antiBox;

    @ViewInject(R.id.tv_switch_ps)
    private TextView tvSwitchPs;

    @ViewInject(R.id.cb_ps)
    private CheckBox psBox;

    @Event(R.id.save)
    private void save(View view) {
        editDeviceParam();
    }

    @Event(R.id.layout_connect)
    private void connect(View view) {
        rebindDialog.show();
    }

    @Event(R.id.layout_delete)
    private void delete(View view) {
        deleteDialog.show();
    }

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();

        initDeleteDialog();
        initRebindDialog();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_DEVICEPARAMINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                DeviceParamInfoResponse response = gson.fromJson(result, DeviceParamInfoResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        bean = response.getData();
                        updateView();
                        break;

                    default:
                        T.s("获取宝宝设置信息失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void editDeviceParam() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITDEVICEPARAM);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("DEVICE_PARAM_ID", bean.getMYPARAM().getDEVICE_PARAM_ID());
        params.addQueryStringParameter("NS_SWITH", bean.getMYPARAM().getNS_SWITH());
        params.addQueryStringParameter("NS_S", bean.getMYPARAM().getNS_F());
        params.addQueryStringParameter("NS_F", bean.getMYPARAM().getNS_F());
        params.addQueryStringParameter("TB_TEMPERATURE", bean.getMYPARAM().getTB_TEMPERATURE());
        params.addQueryStringParameter("TB_SWITH", bean.getMYPARAM().getTB_SWITH());
        params.addQueryStringParameter("FZX_SWITH", bean.getMYPARAM().getFZX_SWITH());
        params.addQueryStringParameter("FZX_TEMPERATURE", bean.getMYPARAM().getFZX_TEMPERATURE());
        params.addQueryStringParameter("PS_SWITH", bean.getMYPARAM().getPS_SWITH());
        params.addQueryStringParameter("QZD_SWITH", bean.getMYPARAM().getQZD_SWITH());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        T.s("保存成功");
                        finish();
                        break;

                    default:
                        T.s("保存失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void unbindDevice() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_UNBINDDEVICE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("解绑成功");
                        finish();
                        break;

                    default:
                        T.s("解绑失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void deleteMember() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_DELETEMEMBER);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("删除成功");
                        finish();
                        break;

                    default:
                        T.s("删除失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void updateView() {
        connectDeviceNo.setText("设备编号 " + bean.getMYPARAM().getDEVICE_PARAM_ID());

        switch (bean.getMYPARAM().getNS_SWITH()) {
            case "1":
                tvSwitchNs.setText("开");
                wakeBox.setChecked(true);
                break;
            case "2":
                tvSwitchNs.setText("关");
                wakeBox.setChecked(false);
                break;
        }

        switch (bean.getMYPARAM().getTB_SWITH()) {
            case "1":
                tvSwitchTb.setText("开");
                kickBox.setChecked(true);
                break;
            case "2":
                tvSwitchTb.setText("关");
                kickBox.setChecked(false);
                break;
        }

        switch (bean.getMYPARAM().getFZX_SWITH()) {
            case "1":
                tvSwitchAnti.setText("开");
                antiBox.setChecked(true);
                break;
            case "2":
                tvSwitchAnti.setText("关");
                antiBox.setChecked(false);
                break;
        }

        switch (bean.getMYPARAM().getPS_SWITH()) {
            case "1":
                tvSwitchPs.setText("开");
                psBox.setChecked(true);
                break;
            case "2":
                tvSwitchPs.setText("关");
                psBox.setChecked(false);
                break;
        }

        nsLow.setText(bean.getDEFAULT().getNS_LOW() + "%");
        nsHeight.setText(bean.getDEFAULT().getNS_HIGH() + "%");
        nsRem.setText("专家推荐值：" + bean.getDEFAULT().getNS_RECOMMEND() + "%");
        nsTv.setText(bean.getMYPARAM().getNS_F() + "%");

        if (!TextUtils.isEmpty(bean.getMYPARAM().getNS_F())) {
            int value = Integer.parseInt(bean.getMYPARAM().getNS_F()) - Integer.parseInt(bean.getDEFAULT().getNS_LOW());
            int offsetMax = Integer.parseInt(bean.getDEFAULT().getNS_HIGH()) - Integer.parseInt(bean.getDEFAULT().getNS_LOW());
            int percent = value * 100 / offsetMax;
            niaoxingSb.setProgress(percent);
            nsTv.setText(bean.getMYPARAM().getNS_F() + "%");
        } else {
            niaoxingSb.setProgress(0);
            nsTv.setText(bean.getDEFAULT().getNS_LOW() + "%");
        }

        if (!TextUtils.isEmpty(bean.getMYPARAM().getTB_TEMPERATURE())) {
            int value = Integer.parseInt(bean.getMYPARAM().getTB_TEMPERATURE()) - Integer.parseInt(bean.getDEFAULT().getTB_LOW());
            int offsetMax = Integer.parseInt(bean.getDEFAULT().getTB_HIGH()) - Integer.parseInt(bean.getDEFAULT().getTB_LOW());
            int percent = value * 100 / offsetMax;
            tibeiSb.setProgress(percent);
            tibeiTv.setText(bean.getMYPARAM().getTB_TEMPERATURE() + "℃");
        } else {
            tibeiSb.setProgress(0);
            tibeiTv.setText(bean.getDEFAULT().getTB_LOW() + "℃");
        }

        if (!TextUtils.isEmpty(bean.getMYPARAM().getFZX_TEMPERATURE())) {
            int value = Integer.parseInt(bean.getMYPARAM().getFZX_TEMPERATURE()) - Integer.parseInt(bean.getDEFAULT().getFZX_LOW());
            int offsetMax = Integer.parseInt(bean.getDEFAULT().getFZX_HIGH()) - Integer.parseInt(bean.getDEFAULT().getFZX_LOW());
            int percent = value * 100 / offsetMax;
            zhixiSb.setProgress(percent);
            zhixiTv.setText(bean.getMYPARAM().getFZX_TEMPERATURE() + "℃");
        } else {
            zhixiSb.setProgress(0);
            zhixiTv.setText(bean.getDEFAULT().getFZX_LOW() + "℃");
        }
    }

    private void initView() {
        tibeiSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                System.out.println(i);
                int offsetMax = 7;
                tibeiTv.setText((20 + offsetMax * i / 100) + "℃");
                bean.getMYPARAM().setTB_TEMPERATURE(String.valueOf((20 + offsetMax * i / 100)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        niaoxingSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                System.out.println(i);
                int offsetMax = 100;
                niaoxingTv.setText((0 + offsetMax * i / 100) + "%");
                bean.getMYPARAM().setNS_F(String.valueOf((0 + offsetMax * i / 100)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        zhixiSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                System.out.println(i);
                int offsetMax = 10;
                zhixiTv.setText((28 + offsetMax * i / 100) + "℃");
                bean.getMYPARAM().setFZX_TEMPERATURE(String.valueOf((28 + offsetMax * i / 100)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        wakeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bean.getMYPARAM().setNS_SWITH("1");
                    tvSwitchNs.setText("开");
                } else {
                    bean.getMYPARAM().setNS_SWITH("2");
                    tvSwitchNs.setText("关");
                }
            }
        });

        kickBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bean.getMYPARAM().setTB_SWITH("1");
                    tvSwitchTb.setText("开");
                } else {
                    bean.getMYPARAM().setTB_SWITH("2");
                    tvSwitchTb.setText("关");
                }
            }
        });

        antiBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bean.getMYPARAM().setFZX_SWITH("1");
                    tvSwitchAnti.setText("开");
                } else {
                    bean.getMYPARAM().setFZX_SWITH("2");
                    tvSwitchAnti.setText("关");
                }
            }
        });

        psBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bean.getMYPARAM().setPS_SWITH("1");
                    tvSwitchPs.setText("开");
                } else {
                    bean.getMYPARAM().setPS_SWITH("2");
                    tvSwitchPs.setText("关");
                }
            }
        });
    }

    private void initDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        deleteDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);
        TextView title = (TextView) view.findViewById(R.id.title);

        title.setText("确定删除宝宝？");

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                deleteDialog.cancel();
            }
        });

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                deleteDialog.cancel();
                deleteMember();
            }
        });

        deleteDialog.setCancelable(false);
//        tipDialog.show();
    }

    private void initRebindDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_pay_tip, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        rebindDialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        Button sureButton = (Button) view.findViewById(R.id.sure);
        TextView title = (TextView) view.findViewById(R.id.title);

        title.setText("确定解绑宝宝？");

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                rebindDialog.cancel();
            }
        });

        sureButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                rebindDialog.cancel();
                unbindDevice();
            }
        });

        rebindDialog.setCancelable(false);
//        tipDialog.show();
    }
}
