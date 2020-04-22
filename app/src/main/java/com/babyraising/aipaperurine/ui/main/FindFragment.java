package com.babyraising.aipaperurine.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.adapter.FindAdapter;
import com.babyraising.aipaperurine.adapter.RankAllAdapter;
import com.babyraising.aipaperurine.adapter.RankWeekAdapter;
import com.babyraising.aipaperurine.base.BaseFragment;
import com.babyraising.aipaperurine.bean.GrowthRankingAllBean;
import com.babyraising.aipaperurine.bean.GrowthRankingBean;
import com.babyraising.aipaperurine.bean.InformationListBean;
import com.babyraising.aipaperurine.bean.InformationTypeBean;
import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.bean.WeekGrowthRankingBean;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.GrowthRankingResponse;
import com.babyraising.aipaperurine.response.InformationListResponse;
import com.babyraising.aipaperurine.response.InformationTypeResponse;
import com.babyraising.aipaperurine.response.UserGrowthRankingResponse;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@ContentView(R.layout.fragment_find)
public class FindFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private int typeInt = 0;
    private String INFO_TYPE = "";
    private UserBean bean;
    private PersonBean personBean;

    private RankAllAdapter rankAllAdapter;
    private RankWeekAdapter rankWeekAdapter;
    private List<GrowthRankingBean> growthRankingAllBeanList;
    private List<WeekGrowthRankingBean> growthRankingWeekBeanList;
    private FindAdapter findAdapter;
    private List<InformationListBean> findList;

    @ViewInject(R.id.tv_tab_find)
    private TextView findTv;

    @ViewInject(R.id.tv_tab_rank)
    private TextView rankTv;

    @ViewInject(R.id.view_tab_find)
    private View findView;

    @ViewInject(R.id.view_tab_rank)
    private View rankView;

    @ViewInject(R.id.rv_all_grow_rank)
    private RecyclerView rvAllGrowRank;

    @ViewInject(R.id.rv_week_grow_rank)
    private RecyclerView rvWeekGrowRank;

    @ViewInject(R.id.main_layout)
    private RelativeLayout mainLayout;

    @ViewInject(R.id.layout_find)
    private LinearLayout findLayout;

    @ViewInject(R.id.layout_rank)
    private LinearLayout rankLayout;

    @ViewInject(R.id.group_find)
    private RadioGroup findGroup;

    @ViewInject(R.id.rv_find)
    private RecyclerView rvFind;

    @Event(R.id.layout_tab_find)
    private void findLayout(View view) {
        if (typeInt != 0) {
            typeInt = 0;

            findView.setVisibility(View.VISIBLE);
            rankView.setVisibility(View.INVISIBLE);

            findTv.setTextSize(24);
            rankTv.setTextSize(14);
            findLayout.setVisibility(View.VISIBLE);
            rankLayout.setVisibility(View.GONE);
        }
    }

    @Event(R.id.layout_tab_rank)
    private void rankLayout(View view) {
        if (typeInt != 1) {
            typeInt = 1;

            findView.setVisibility(View.INVISIBLE);
            rankView.setVisibility(View.VISIBLE);

            findTv.setTextSize(14);
            rankTv.setTextSize(24);
            findLayout.setVisibility(View.GONE);
            rankLayout.setVisibility(View.VISIBLE);
        }
    }

    private AlertDialog rankDetailDialog;
    private PopupWindow popupWindow;
    private TextView name;
    private TextView time;
    private TextView all_rank;
    private TextView week_rank;
    private TextView all_change_urine;
    private TextView all_volume;
    private TextView all_sign;
    private ImageView icon;

    public FindFragment() {
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
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
        initRankDetailDialog();
        updateRankingList();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getActivity().getApplication()).getUserInfo();
        personBean = ((PaperUrineApplication) getActivity().getApplication()).getPersonInfo();
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_INFORMATIONTYPE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                InformationTypeResponse response = gson.fromJson(result, InformationTypeResponse.class);
                switch (response.getResult()) {
                    case 0:
//                        System.out.println(result);
                        updateTypeList(response.getData());
//                        updateInfoList();
                        break;
                    default:

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

    private void updateTypeList(final List<InformationTypeBean> datas) {
        for (int d = 0; d < datas.size(); d++) {
            if (d == 0) {
                RadioButton rb = new RadioButton(getActivity());
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(225, 102);
                rb.setBackgroundResource(R.drawable.select_find_tab);
                rb.setButtonDrawable(null);
                rb.setGravity(Gravity.CENTER);
                rb.setText(datas.get(d).getTYPENAME());
                ColorStateList csl = getResources().getColorStateList(R.color.select_find_tab);
                rb.setTextColor(csl);
                rb.setTextSize(14);
                rb.setLayoutParams(params);
                findGroup.addView(rb);
                final int finalD = d;
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        INFO_TYPE = datas.get(finalD).getINFORMATIONTYPE_ID();
                        updateInfoList();
                    }
                });
                rb.setChecked(true);
            } else {
                View view = new View(getActivity());
                RadioGroup.LayoutParams viewParams = new RadioGroup.LayoutParams(0, 68, 1);
                view.setLayoutParams(viewParams);
                view.setBackgroundDrawable(null);
                findGroup.addView(view);

                RadioButton rb = new RadioButton(getActivity());
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(225, 102);
                rb.setBackgroundResource(R.drawable.select_find_tab);
                rb.setButtonDrawable(null);
                rb.setGravity(Gravity.CENTER);
                rb.setText(datas.get(d).getTYPENAME());
                ColorStateList csl = getResources().getColorStateList(R.color.select_find_tab);
                rb.setTextColor(csl);
                rb.setTextSize(14);
                rb.setLayoutParams(params);
                findGroup.addView(rb);
                final int finalD = d;
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        INFO_TYPE =datas.get(finalD).getINFORMATIONTYPE_ID();
                        updateInfoList();
                    }
                });
            }
        }

        if (datas.size() > 0) {
            INFO_TYPE =datas.get(0).getINFORMATIONTYPE_ID();
            updateInfoList();
        }

    }

    private void updateRankingList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GROWTHRANKING);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GrowthRankingResponse response = gson.fromJson(result, GrowthRankingResponse.class);
                switch (response.getResult()) {
                    case 0:
                        //全部排名
                        growthRankingAllBeanList.clear();
                        for (int a = 0; a < response.getData().getGrowthRanking().size(); a++) {
                            growthRankingAllBeanList.add(response.getData().getGrowthRanking().get(a));
                        }
                        GrowthRankingBean meAllBean = new GrowthRankingBean();
                        meAllBean.setAPPUSER_ID(response.getData().getMyGrowthRanking());
                        meAllBean.setHEADIMG(personBean.getHEADIMG());
                        meAllBean.setNICKNAME(personBean.getNICKNAME());
                        growthRankingAllBeanList.add(meAllBean);
                        rankAllAdapter.notifyDataSetChanged();

                        //周排名
                        growthRankingWeekBeanList.clear();
                        for (int b = 0; b < response.getData().getWeekGrowthRanking().size(); b++) {
                            growthRankingWeekBeanList.add(response.getData().getWeekGrowthRanking().get(b));
                        }
                        WeekGrowthRankingBean meWeekBean = new WeekGrowthRankingBean();
                        meWeekBean.setAPPUSER_ID(response.getData().getMyWeekGrowthRanking());
                        meWeekBean.setHEADIMG(personBean.getHEADIMG());
                        meWeekBean.setNICKNAME(personBean.getNICKNAME());
                        growthRankingWeekBeanList.add(meWeekBean);
                        rankWeekAdapter.notifyDataSetChanged();
                        break;
                    default:

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

    private void updateInfoList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_INFORMATIONLIST);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("INFORMATIONTYPE_ID", INFO_TYPE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                InformationListResponse response = gson.fromJson(result, InformationListResponse.class);
                switch (response.getResult()) {
                    case 0:
                        System.out.println(result);
                        findList.clear();
                        for (int i = 0; i < response.getData().size(); i++) {
                            findList.add(response.getData().get(i));
                        }
                        findAdapter.notifyDataSetChanged();
                        break;
                    default:

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

    private void initView() {

        growthRankingAllBeanList = new ArrayList<>();
        rankAllAdapter = new RankAllAdapter(growthRankingAllBeanList);
        LinearLayoutManager allManager = new LinearLayoutManager(getActivity());
        rvAllGrowRank.setAdapter(rankAllAdapter);
        rvAllGrowRank.setLayoutManager(allManager);
        rankAllAdapter.setOnItemClickListener(new RankAllAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (position == growthRankingAllBeanList.size() - 1) {
                    startRankShareActivity();
                } else {
                    showRankDetailDialog(growthRankingAllBeanList.get(position).getAPPUSER_ID());
                }
            }
        });

        growthRankingWeekBeanList = new ArrayList<>();
        rankWeekAdapter = new RankWeekAdapter(growthRankingWeekBeanList);
        LinearLayoutManager weekManager = new LinearLayoutManager(getActivity());
        rvWeekGrowRank.setAdapter(rankWeekAdapter);
        rvWeekGrowRank.setLayoutManager(weekManager);
        rankWeekAdapter.setOnItemClickListener(new RankWeekAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (position == growthRankingAllBeanList.size() - 1) {
                    startRankShareActivity();
                } else {
                    showRankDetailDialog(growthRankingWeekBeanList.get(position).getAPPUSER_ID());
                }
            }
        });

        findList = new ArrayList<>();
        findAdapter = new FindAdapter(findList);
        LinearLayoutManager findManger = new LinearLayoutManager(getActivity());

        findAdapter.setOnItemClickListener(new FindAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startInfoDetailActivity(findList.get(position).getINFORMATION_ID());
            }
        });

        rvFind.setLayoutManager(findManger);
        rvFind.setAdapter(findAdapter);
    }

    private void startInfoDetailActivity(String infoId) {
        Intent intent = new Intent(getActivity(), FindDetailActivity.class);
        intent.putExtra("infoId", infoId);
        startActivity(intent);
    }

    private void startRankShareActivity() {
        Intent intent = new Intent(getActivity(), RankShareActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateInfoList();
    }

    private void showRankDetailDialog(String ID) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_USERGROWTHRANKING);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("ID", ID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                UserGrowthRankingResponse response = gson.fromJson(result, UserGrowthRankingResponse.class);
                switch (response.getResult()) {
                    case 0:
                        name.setText(response.getData().getNICKNAME());
                        time.setText("");
                        all_rank.setText(response.getData().getMyGrowthRanking());
                        week_rank.setText(response.getData().getMyWeekGrowthRanking());
                        all_sign.setText(response.getData().getSIGNIN_ALL());
                        all_change_urine.setText(response.getData().getDIAPER_ALL());
                        all_volume.setText(response.getData().getURINE_VOLUME_ALL());
                        ImageOptions options = new ImageOptions.Builder().
                                setRadius(DensityUtil.dip2px(54)).setCrop(true).build();
                        x.image().bind(icon, response.getData().getHEADIMG(), options);
                        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
                        break;
                    default:
                        T.s("获取排名详情失败");
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

    private void initRankDetailDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.dialog);
//        // 创建一个view，并且将布局加入view中
//        View view = LayoutInflater.from(getActivity()).inflate(
//                R.layout.dialog_rank_detail, null, false);
//        // 将view添加到builder中
//        builder.setView(view);
//        // 创建dialog
//        rankDetailDialog = builder.create();
//        // 初始化控件，注意这里是通过view.findViewById
//        Button btClose = (Button) view.findViewById(R.id.bt_close);
//        btClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rankDetailDialog.cancel();
//            }
//        });
//        Window dialogWindow = rankDetailDialog.getWindow();
//        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
//        name = (TextView) view.findViewById(R.id.name);
//        time = (TextView) view.findViewById(R.id.time);
//        all_rank = (TextView) view.findViewById(R.id.all_rank);
//        week_rank = (TextView) view.findViewById(R.id.week_rank);
//        all_change_urine = (TextView) view.findViewById(R.id.all_change_urine);
//        all_volume = (TextView) view.findViewById(R.id.all_volume);
//        all_sign = (TextView) view.findViewById(R.id.all_sign);
//        icon = (ImageView) view.findViewById(R.id.icon);
//
//        rankDetailDialog.setCancelable(false);
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_rank_detail, null, false);
        name = (TextView) view.findViewById(R.id.name);
        time = (TextView) view.findViewById(R.id.time);
        all_rank = (TextView) view.findViewById(R.id.all_rank);
        week_rank = (TextView) view.findViewById(R.id.week_rank);
        all_change_urine = (TextView) view.findViewById(R.id.all_change_urine);
        all_volume = (TextView) view.findViewById(R.id.all_volume);
        all_sign = (TextView) view.findViewById(R.id.all_sign);
        icon = (ImageView) view.findViewById(R.id.icon);

        popupWindow = new PopupWindow(getActivity());
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
}
