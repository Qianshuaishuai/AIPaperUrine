package com.xinxin.aicare.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.InformationBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.InformationResponse;
import com.xinxin.aicare.util.T;
import com.xinxin.aicare.util.WxShareUtils;
import com.google.gson.Gson;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_find_detail)
public class FindDetailActivity extends BaseActivity {

    private UserBean bean;
    private InformationBean infoBean;
    private String infoId;

    @ViewInject(R.id.videoView)
    private VideoView videoView;

    @Event(R.id.layout_back)
    private void layoutBack(View view) {
        finish();
    }

    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.icon)
    private ImageView icon;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.reading_count)
    private TextView reading_count;

    @ViewInject(R.id.word_count)
    private TextView word_count;

    @ViewInject(R.id.content)
    private HtmlTextView content;

    @ViewInject(R.id.cb_sc)
    private CheckBox cb_sc;

    @ViewInject(R.id.cb_dz)
    private CheckBox cb_dz;

    @ViewInject(R.id.bt_share)
    private Button bt_share;

    @ViewInject(R.id.scrollview)
    private ScrollView scrollView;

    @ViewInject(R.id.layout_share_all)
    private RelativeLayout layoutShare;

    @Event(R.id.layout_share_1)
    private void share1Click(View view){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);
        WxShareUtils.shareWeb(this, Constant.WX_APPID, "http://wechatshare.xinxinchoice.com/bs/share.html?id=91d58cc6e39a4915bbd9cce276e832e3", infoBean.getTITLE(), infoBean.getINFO(), bmp, 1);
        layoutShare.setVisibility(View.GONE);
    }

    @Event(R.id.layout_share_2)
    private void share2Click(View view){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);
        WxShareUtils.shareWeb(this, Constant.WX_APPID, "http://wechatshare.xinxinchoice.com/bs/share.html?id=91d58cc6e39a4915bbd9cce276e832e3", infoBean.getTITLE(), infoBean.getINFO(), bmp, 2);
        layoutShare.setVisibility(View.GONE);
    }

    @Event(R.id.bt_share)
    private void share(View view) {
        layoutShare.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        bean = ((PaperUrineApplication) getApplication()).getUserInfo();
        Intent intent = getIntent();
        infoId = intent.getStringExtra("infoId");
        if (TextUtils.isEmpty(infoId)) {
            T.s("获取文章详情失败");
            return;
        }

        updateData();
    }

    private void initView() {

    }

    private void shareSuccess(){
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SHARESUCCESS);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                InformationResponse response = gson.fromJson(result, InformationResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("分享成功");
                        break;
                    default:
                        T.s("分享失败");
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

    private void updateData() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_INFORMATION);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("INFORMATION_ID", infoId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                InformationResponse response = gson.fromJson(result, InformationResponse.class);
                switch (response.getResult()) {
                    case 0:
                        infoBean = response.getData();
                        updateView();
                        break;
                    default:
                        T.s("获取文章详情失败");
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
    protected void onResume() {
        super.onResume();

    }

    private void updateView() {
        x.image().bind(icon, infoBean.getPIC());
        title.setText(infoBean.getTITLE());
        name.setText(infoBean.getPUBLISHNAME());
        time.setText(infoBean.getPUBLISHTIME());
        reading_count.setText("阅读数 " + infoBean.getREADNUM());
        word_count.setText("字数 " + infoBean.getDETAIL().length());
//        content.setText(infoBean.getDETAIL());
        content.setHtml(infoBean.getDETAIL(),
                new HtmlResImageGetter(this));
        if (infoBean.getIS_STAR().equals("1")) {
            cb_dz.setChecked(true);
        } else if (infoBean.getIS_STAR().equals("2")) {
            cb_dz.setChecked(false);
        }

        if (infoBean.getIS_COLLECT().equals("1")) {
            cb_sc.setChecked(true);
        } else if (infoBean.getIS_COLLECT().equals("2")) {
            cb_sc.setChecked(false);
        }

        cb_sc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String type = b ? "1" : "2";
                collect(type);
            }
        });

        cb_dz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String type = b ? "1" : "2";
                star(type);
            }
        });

        if (!TextUtils.isEmpty(infoBean.getVIDEO())) {
            videoView.setVisibility(View.VISIBLE);
            MediaController localMediaController = new MediaController(this);
            videoView.setMediaController(localMediaController);
            String url = infoBean.getVIDEO();
            videoView.setVideoPath(url);
            videoView.start();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
            params.topMargin = 700;
            scrollView.setLayoutParams(params);
        }
    }

    private void collect(final String type) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_INFORMATIONCOLLECT);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("INFORMATION_ID", infoId);
        params.addQueryStringParameter("TYPE", type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        if (type == "1") {
                            T.s("收藏成功");
                        } else if (type == "2") {
                            T.s("取消收藏成功");
                        }

                        break;
                    default:
                        if (type == "1") {
                            T.s("收藏失败");
                        } else if (type == "2") {
                            T.s("取消收藏失败");
                        }
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

    private void star(final String type) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_INFORMATIONSTAR);
        params.addQueryStringParameter("APPUSER_ID", bean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", bean.getONLINE_ID());
        params.addQueryStringParameter("INFORMATION_ID", infoId);
        params.addQueryStringParameter("TYPE", type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        if (type == "1") {
                            T.s("点赞成功");
                        } else if (type == "2") {
                            T.s("取消点赞成功");
                        }
                        break;
                    default:
                        if (type == "1") {
                            T.s("点赞失败");
                        } else if (type == "2") {
                            T.s("取消点赞失败");
                        }
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
}
