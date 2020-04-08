package com.babyraising.aipaperurine.ui.person;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.SignInfoResponse;
import com.babyraising.aipaperurine.response.SigninResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_sign)
public class SignActivity extends BaseActivity {

    private UserBean userBean;
    private PersonBean personBean;
    private AlertDialog tipDialog;
    private TextView detail;
    private PopupWindow popupWindow;
    private int currentDay;

    @ViewInject(R.id.sign_day)
    private TextView signDay;

    @ViewInject(R.id.main_layout)
    private RelativeLayout mainLayout;

    @ViewInject(R.id.integral)
    private TextView integral;

    @ViewInject(R.id.sign_complete)
    private Button signComplete;

    @ViewInject(R.id.iv1)
    private ImageView iv1;

    @ViewInject(R.id.iv2)
    private ImageView iv2;

    @ViewInject(R.id.iv3)
    private ImageView iv3;

    @ViewInject(R.id.iv4)
    private ImageView iv4;

    @ViewInject(R.id.iv5)
    private ImageView iv5;

    @ViewInject(R.id.iv6)
    private ImageView iv6;

    @ViewInject(R.id.iv7)
    private ImageView iv7;

    @ViewInject(R.id.tv1)
    private TextView tv1;

    @ViewInject(R.id.tv2)
    private TextView tv2;

    @ViewInject(R.id.tv3)
    private TextView tv3;

    @ViewInject(R.id.tv4)
    private TextView tv4;

    @ViewInject(R.id.tv5)
    private TextView tv5;

    @ViewInject(R.id.tv6)
    private TextView tv6;

    @ViewInject(R.id.tv7)
    private TextView tv7;

    @ViewInject(R.id.view1)
    private View view1;

    @ViewInject(R.id.view2)
    private View view2;

    @ViewInject(R.id.view3)
    private View view3;

    @ViewInject(R.id.view4)
    private View view4;

    @ViewInject(R.id.view5)
    private View view5;

    @ViewInject(R.id.view6)
    private View view6;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.sign_complete)
    private void signComplete(View view) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SIGNIN);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                SigninResponse response = gson.fromJson(result, SigninResponse.class);
                switch (response.getResult()) {
                    case 0:
                        detail.setText("+" + response.getData().getPOINT() + "分");
                        integral.setText(currentDay + 1);
                        updateSignList(currentDay + 1);
                        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

                        signComplete.setClickable(false);
                        signComplete.setText("已签到");
                        signComplete.setTextColor(getResources().getColor(R.color.colorSign));
                        signComplete.setBackgroundResource(R.drawable.shape_sign_bt_selected_bg);
                        break;
                    default:
                        T.s("签到失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误处理:" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initTipDialog();
    }

    private void initTipDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialog);
//
//        // 创建一个view，并且将布局加入view中
//        View view = LayoutInflater.from(this).inflate(
//                R.layout.dialog_sign_success, null, false);
//        // 将view添加到builder中
//        builder.setView(view);
//        // 创建dialog
//        tipDialog = builder.create();
//        // 初始化控件，注意这里是通过view.findViewById
//        detail = (TextView) view.findViewById(R.id.detail);
//
//        Button btClose = (Button) view.findViewById(R.id.bt_close);
//
//        btClose.setOnClickListener(new android.view.View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                tipDialog.cancel();
//            }
//        });
//
//        tipDialog.setCancelable(false);
//        tipDialog.show();

        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_sign_success, null, false);
        detail = (TextView) view.findViewById(R.id.detail);

        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        Button btClose = (Button) view.findViewById(R.id.bt_close);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void initView() {

    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        personBean = ((PaperUrineApplication) getApplication()).getPersonInfo();
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SIGNININFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                SignInfoResponse response = gson.fromJson(result, SignInfoResponse.class);
                switch (response.getResult()) {
                    case 0:
                        signDay.setText(response.getData().getCONTINUITY_SIGNIN_NUM());
                        integral.setText(personBean.getPOINT());
                        updateSignList(Integer.parseInt(response.getData().getCONTINUITY_SIGNIN_NUM()));
                        currentDay = Integer.parseInt(response.getData().getCONTINUITY_SIGNIN_NUM());
                        switch (response.getData().getHAS_SIGNIN()) {
                            case "1":
                                signComplete.setClickable(true);
                                signComplete.setText("签到");
                                signComplete.setTextColor(getResources().getColor(R.color.colorWhite));
                                signComplete.setBackgroundResource(R.drawable.shape_sign_bt_bg);
                                break;
                            case "2":
                                signComplete.setClickable(false);
                                signComplete.setText("已签到");
                                signComplete.setTextColor(getResources().getColor(R.color.colorSign));
                                signComplete.setBackgroundResource(R.drawable.shape_sign_bt_selected_bg);
                                break;
                        }
                        break;
                    default:
                        T.s("获取签到信息失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误处理:" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void updateSignList(int day) {
        for (int d = 0; d < day; d++) {
            switch (d + 1) {
                case 1:
                    iv1.setImageResource(R.mipmap.icon_jifen_light);
                    tv1.setTextColor(getResources().getColor(R.color.colorCommon));
                    break;
                case 2:
                    iv2.setImageResource(R.mipmap.icon_jifen_light);
                    tv2.setTextColor(getResources().getColor(R.color.colorCommon));
                    view1.setBackgroundColor(getResources().getColor(R.color.colorCommon));
                    break;
                case 3:
                    iv3.setImageResource(R.mipmap.icon_jifen_light);
                    tv3.setTextColor(getResources().getColor(R.color.colorCommon));
                    view2.setBackgroundColor(getResources().getColor(R.color.colorCommon));
                    break;
                case 4:
                    iv4.setImageResource(R.mipmap.icon_jifen_light);
                    tv4.setTextColor(getResources().getColor(R.color.colorCommon));
                    view3.setBackgroundColor(getResources().getColor(R.color.colorCommon));
                    break;
                case 5:
                    iv5.setImageResource(R.mipmap.icon_jifen_light);
                    tv5.setTextColor(getResources().getColor(R.color.colorCommon));
                    view4.setBackgroundColor(getResources().getColor(R.color.colorCommon));
                    break;
                case 6:
                    iv6.setImageResource(R.mipmap.icon_jifen_light);
                    tv6.setTextColor(getResources().getColor(R.color.colorCommon));
                    view5.setBackgroundColor(getResources().getColor(R.color.colorCommon));
                    break;
                case 7:
                    iv7.setImageResource(R.mipmap.icon_jifen_light);
                    tv7.setTextColor(getResources().getColor(R.color.colorCommon));
                    view6.setBackgroundColor(getResources().getColor(R.color.colorCommon));
                    break;
            }
        }
    }
}
