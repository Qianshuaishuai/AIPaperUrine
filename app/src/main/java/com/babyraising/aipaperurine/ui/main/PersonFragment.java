package com.babyraising.aipaperurine.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseFragment;
import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.PersonResponse;
import com.babyraising.aipaperurine.response.RegisterResponse;
import com.babyraising.aipaperurine.response.WaitReadResponse;
import com.babyraising.aipaperurine.ui.address.AddressManagerActivity;
import com.babyraising.aipaperurine.ui.message.MessageActivity;
import com.babyraising.aipaperurine.ui.order.OrderActivity;
import com.babyraising.aipaperurine.ui.person.CouponActivity;
import com.babyraising.aipaperurine.ui.person.GrowthActivity;
import com.babyraising.aipaperurine.ui.person.SignActivity;
import com.babyraising.aipaperurine.ui.setting.SettingActivity;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@ContentView(R.layout.fragment_person)
public class PersonFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private UserBean userBean;
    private PersonBean personBean;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.day)
    private TextView day;

    @ViewInject(R.id.dz_count)
    private TextView dzCount;

    @ViewInject(R.id.sc_count)
    private TextView scCount;

    @ViewInject(R.id.browse_count)
    private TextView browseCount;

    @ViewInject(R.id.integral_count)
    private TextView integralCount;

    @ViewInject(R.id.icon)
    private ImageView icon;

    @Event(R.id.sign_layout)
    private void signLayoutClick(View view) {
        Intent intent = new Intent(getActivity(), SignActivity.class);
        startActivity(intent);
    }

    @Event(R.id.message)
    private void messageLayoutClick(View view) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        startActivity(intent);
    }

    @Event(R.id.growth_layout)
    private void GrowthLayout(View view) {
        Intent intent = new Intent(getActivity(), GrowthActivity.class);
        startActivity(intent);
    }

    @Event(R.id.coupon_layout)
    private void couponLayout(View view) {
        Intent intent = new Intent(getActivity(), CouponActivity.class);
        startActivity(intent);
    }

    @Event(R.id.setting_layout)
    private void settingLayout(View view) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    @Event(R.id.order_layout)
    private void orderLayout(View view) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        startActivity(intent);
    }

    @Event(R.id.address_layout)
    private void addressLayout(View view) {
        Intent intent = new Intent(getActivity(), AddressManagerActivity.class);
        startActivity(intent);
    }

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
        initMessageRead();
    }

    private void initMessageRead() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_CHECKMESSAGEREAD);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                WaitReadResponse response = gson.fromJson(result, WaitReadResponse.class);
                switch (response.getResult()) {
                    case 0:
                        if (TextUtils.isEmpty(response.getData().getHAS_WAITREAD())) {

                        }

                        System.out.println("未读消息:" + response.getData().getHAS_WAITREAD());
                        break;

                    default:
                        T.s("获取未读消息失败");
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

    private void initData() {
        userBean = ((PaperUrineApplication) getActivity().getApplication()).getUserInfo();

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PERSONINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PersonResponse response = gson.fromJson(result, PersonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        ((PaperUrineApplication) getActivity().getApplication()).savePersonInfo(response.getData());
                        personBean = response.getData();
                        updateView();
                        break;

                    default:
                        T.s("获取个人信息失败");
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
        if (personBean.getNICKNAME().equals("")) {
            name.setText("尚未设置昵称");
        } else {
            name.setText(personBean.getNICKNAME());
        }
        ImageOptions options = new ImageOptions.Builder().
                setRadius(DensityUtil.dip2px(60)).build();
        x.image().bind(icon, personBean.getHEADIMG(), options);

        day.setText(personBean.getJOINDAYS() + "天");

        dzCount.setText(personBean.getSTANUM());
        scCount.setText(personBean.getCOLLECTNUM());
        integralCount.setText(personBean.getPOINT());
        browseCount.setText(personBean.getREADNUM());

        if (TextUtils.isEmpty(personBean.getSTANUM())) {
            dzCount.setText("0");
        }

        if (TextUtils.isEmpty(personBean.getCOLLECTNUM())) {
            scCount.setText("0");
        }

        if (TextUtils.isEmpty(personBean.getPOINT())) {
            integralCount.setText("0");
        }

        if (TextUtils.isEmpty(personBean.getREADNUM())) {
            browseCount.setText("0");
        }
    }

    private void initView() {

    }
}
