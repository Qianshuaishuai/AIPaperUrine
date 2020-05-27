package com.xinxin.aicare.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.MemberAdapter;
import com.xinxin.aicare.base.BaseFragment;
import com.xinxin.aicare.bean.MemberListBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CourseResponse;
import com.xinxin.aicare.response.MemberListResponse;
import com.xinxin.aicare.ui.baby.AccessInductActivity;
import com.xinxin.aicare.ui.info.BabyBindActivity;
import com.xinxin.aicare.ui.info.CallSettingActivity;
import com.xinxin.aicare.ui.info.ChangeBabyInfoActivity;
import com.xinxin.aicare.ui.info.PickSizeActivity;
import com.xinxin.aicare.ui.info.SleepPostureActivity;
import com.xinxin.aicare.ui.info.TeachActivity;
import com.xinxin.aicare.ui.info.UrineDetailActivity;
import com.xinxin.aicare.ui.message.MessageActivity;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserBean userBean;
    private List<MemberListBean> memberList;
    private MemberAdapter adapter;

    private OnFragmentInteractionListener mListener;

    private PopupWindow loginPopupWindow;
    private PopupWindow bindPopupWindow;


    private Timer timer1;
    private TimerTask timerTask1;
    private boolean isFailed = false;

    @ViewInject(R.id.card_name)
    private TextView cardName;

    @ViewInject(R.id.card_icon)
    private ImageView cardIcon;

    @ViewInject(R.id.teach_pic)
    private ImageView teachPic;

    @ViewInject(R.id.rv_member)
    private RecyclerView rvMember;

    @ViewInject(R.id.baby_layout_simple)
    private LinearLayout babyLayout;

    @ViewInject(R.id.card_time)
    private TextView cardTime;

    @ViewInject(R.id.layout_introduce)
    private RelativeLayout introduceLayout;

    @ViewInject(R.id.layout_use)
    private ScrollView useLayout;

    @Event(R.id.card_time)
    private void cardTime(View view) {

    }

    @Event(R.id.layout_add_baby)
    private void addBaby(View view) {
        Intent intent = new Intent(getContext(), BabyBindActivity.class);
        startActivity(intent);
    }

    @Event(R.id.introduce_close)
    private void introduceClose(View view) {
        if (introduceLayout.getVisibility() == View.VISIBLE) {
            introduceLayout.setVisibility(View.GONE);
        }
    }

    @Event(R.id.use_close)
    private void useClose(View view) {
        if (useLayout.getVisibility() == View.VISIBLE) {
            useLayout.setVisibility(View.GONE);
        }
    }

    @Event(R.id.layout_teach)
    private void layoutTeach(View view) {
        Intent intent = new Intent(getActivity(), TeachActivity.class);
        startActivity(intent);
    }

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer1.cancel();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initTeachs();
        initLoginSuccessDialog();
        initBindSuccessDialog();

    }

    private void initTeachs() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GETCOURSE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CourseResponse response = gson.fromJson(result, CourseResponse.class);
                switch (response.getResult()) {
                    case 0:
                        x.image().bind(teachPic, response.getData().getPIC());
                        break;

                    default:
                        T.s("获取视频教程失败");
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
//        if (TextUtils.isEmpty(userBean.getNICKNAME())) {
//            cardName.setText("尚未设置昵称");
//        } else {
//            cardName.setText(userBean.getNICKNAME());
//        }
//
//        ImageOptions options = new ImageOptions.Builder().
//                setRadius(DensityUtil.dip2px(60)).build();
//        x.image().bind(cardIcon, userBean.getHEADIMG(), options);
        timer1 = new Timer();
        timerTask1 = new TimerTask() {
            @Override
            public void run() {
                if (!isFailed){
                    getMemberList();
                }
            }
        };
        timer1.schedule(timerTask1, 0, 1000);
    }

    private void initView() {
        memberList = new ArrayList<>();
        adapter = new MemberAdapter(memberList, this);
        adapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvMember.setLayoutManager(manager);
        rvMember.setAdapter(adapter);
    }

    private void getMemberList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERLIST);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MemberListResponse response = gson.fromJson(result, MemberListResponse.class);
                System.out.println(result);
                switch (response.getResult()) {
                    case 0:
                        if (response.getData().size() == 0) {
                            rvMember.setVisibility(View.GONE);
                            babyLayout.setVisibility(View.VISIBLE);
                        } else {
                            rvMember.setVisibility(View.VISIBLE);
                            babyLayout.setVisibility(View.GONE);

                            memberList.clear();
                            for (int m = 0; m < response.getData().size(); m++) {
                                memberList.add(response.getData().get(m));
                            }

                            adapter.notifyDataSetChanged();
                        }
                        break;

                    default:
//                        T.s("获取宝宝列表失败");
                        isFailed = true;
                        System.out.println(result);
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

    private void initLoginSuccessDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_login_success, null, false);

        Button btClose = (Button) view.findViewById(R.id.bt_close);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPopupWindow.dismiss();
            }
        });
    }

    private void initBindSuccessDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_bind_success, null, false);

        Button btClose = (Button) view.findViewById(R.id.bt_close);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPopupWindow.dismiss();
            }
        });
    }

    public void goToBabyInfoSetting(MemberListBean memberBean) {
        Gson gson = new Gson();
        Intent intent = new Intent(getContext(), ChangeBabyInfoActivity.class);
        String beanStr = gson.toJson(memberBean);
        intent.putExtra("memberBean", beanStr);
        startActivity(intent);
    }

    public void goToDeviceConnect(String memberId) {
        Intent intent = new Intent(getContext(), AccessInductActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    public void goToMemberSetting(String memberId) {
        Intent intent = new Intent(getContext(), CallSettingActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    public void goToMemberMessage(String memberId) {
        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    public void goToPickSizeActivity(String memberId) {
        Intent intent = new Intent(getContext(), PickSizeActivity.class);
        intent.putExtra("mode", 10001);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    public void goToUrineDetailActivity(String memberId) {
        Intent intent = new Intent(getContext(), UrineDetailActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    public void goToPostureActivity(String posture) {
        Intent intent = new Intent(getContext(), SleepPostureActivity.class);
        intent.putExtra("posture", posture);
        startActivity(intent);
    }
}
