package com.xinxin.aicare.ui.info;

import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.response.CourseResponse;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_teach)
public class TeachActivity extends BaseActivity {

    @ViewInject(R.id.videoView)
    private VideoView videoView;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_GETCOURSE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CourseResponse response = gson.fromJson(result, CourseResponse.class);
                switch (response.getResult()) {
                    case 0:
                        initNetVideo(response.getData().getVIDEO());
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

    private void initView() {

    }

    //播放网络视频
    private void initNetVideo(String videoUrl) {
        //设置有进度条可以拖动快进
        MediaController localMediaController = new MediaController(this);
        videoView.setMediaController(localMediaController);
        String url = videoUrl;
        videoView.setVideoPath(url);
        videoView.start();
    }
}
