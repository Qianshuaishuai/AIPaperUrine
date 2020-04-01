package com.babyraising.aipaperurine.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseFragment;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CourseResponse;
import com.babyraising.aipaperurine.response.MemberListResponse;
import com.babyraising.aipaperurine.response.PersonResponse;
import com.babyraising.aipaperurine.ui.info.TeachActivity;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.w3c.dom.Text;
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

    private OnFragmentInteractionListener mListener;

    @ViewInject(R.id.card_name)
    private TextView cardName;

    @ViewInject(R.id.card_icon)
    private ImageView cardIcon;

    @ViewInject(R.id.teach_pic)
    private ImageView teachPic;

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
//        userBean = ((PaperUrineApplication) getActivity().getApplication()).getUserInfo();
//        if (TextUtils.isEmpty(userBean.getNICKNAME())) {
//            cardName.setText("尚未设置昵称");
//        } else {
//            cardName.setText(userBean.getNICKNAME());
//        }
//
//        ImageOptions options = new ImageOptions.Builder().
//                setRadius(DensityUtil.dip2px(60)).build();
//        x.image().bind(cardIcon, userBean.getHEADIMG(), options);
    }

    private void initView() {

    }

    private void getMemberList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERLIST);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MemberListResponse response = gson.fromJson(result, MemberListResponse.class);
                switch (response.getResult()) {
                    case 0:
                        if (response.getData().size() == 0) {

                        }
                        break;

                    default:
                        T.s("获取宝宝列表失败");
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
}
