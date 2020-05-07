package com.xinxin.aicare.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.GoodsAdapter;
import com.xinxin.aicare.base.BaseFragment;
import com.xinxin.aicare.bean.GoodsForStoreBean;
import com.xinxin.aicare.bean.HomeCarouselBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.GoodsListResponse;
import com.xinxin.aicare.response.HomeCarouselResponse;
import com.xinxin.aicare.ui.store.GoodActivity;
import com.xinxin.aicare.util.BannerImageLoader;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@ContentView(R.layout.fragment_store)
public class StoreFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserBean bean;

    @ViewInject(R.id.banner)
    private Banner banner;

    @ViewInject(R.id.rv_store)
    private RecyclerView rvStore;

    private OnFragmentInteractionListener mListener;
    private List<String> bannerImages = new ArrayList<>();
    private List<HomeCarouselBean> bannerBeans = new ArrayList<>();
    private List<GoodsForStoreBean> goodsList = new ArrayList<>();
    private GoodsAdapter adapter;

    public StoreFragment() {
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
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
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
        initStoreList();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getActivity().getApplication()).getUserInfo();
    }

    private void initStoreList() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GOODSLIST);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GoodsListResponse response = gson.fromJson(result, GoodsListResponse.class);
                switch (response.getResult()) {
                    case 0:
                        goodsList.clear();
                        for (int a = 0; a < response.getData().size(); a++) {
                            goodsList.add(response.getData().get(a));
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        System.out.println("获取失败:" + result);
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

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_HOMECAROUSEL);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                HomeCarouselResponse response = gson.fromJson(result, HomeCarouselResponse.class);
                switch (response.getResult()) {
                    case 0:
                        for (int a = 0; a < response.getData().size(); a++) {
                            bannerImages.add(response.getData().get(a).getHOMEPIC());
                            bannerBeans.add(response.getData().get(a));
                        }

                        //设置图片加载器
                        banner.setImageLoader(new BannerImageLoader());
                        //设置图片集合
                        banner.setImages(bannerImages);
                        //banner设置方法全部调用完毕时最后调用
                        banner.start();

                        banner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {
                                System.out.println(bannerBeans.get(position).getGOODS_ID());
                            }
                        });
                        break;
                    default:
                        System.out.println("获取失败:" + result);
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

        adapter = new GoodsAdapter(goodsList);
        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startGoodDetailActivity(position);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvStore.setLayoutManager(manager);
        rvStore.setAdapter(adapter);
    }

    private void startGoodDetailActivity(int position) {
        Intent intent = new Intent(getActivity(), GoodActivity.class);
        intent.putExtra("goodsId", goodsList.get(position).getGOODS_ID());
        startActivity(intent);
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
}
