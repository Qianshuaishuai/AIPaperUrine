package com.xinxin.aicare.ui.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.adapter.ReasonAdapter;
import com.xinxin.aicare.adapter.RefundPhotoAdapter;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.PreRefundMyYuyueBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.PreRefundMyYuyueResponse;
import com.xinxin.aicare.response.PreRefundNumResponse;
import com.xinxin.aicare.util.FileUtil;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_refund)
public class RefundActivity extends BaseActivity {

    private UserBean userBean;
    private PreRefundMyYuyueBean bean;
    private String yuyueId = "";
    private int maxCount = 0;
    private int currentCount = 0;

    public static final int RC_TAKE_PHOTO = 1;
    public static final int RC_CHOOSE_PHOTO = 2;

    private String mTempPhotoPath;
    private Uri imageUri;

    private int receiveType = 1;
    private List<String> reasonList;
    private List<String> photoList;
    private ReasonAdapter reasonAdapter;
    private RefundPhotoAdapter photoAdapter;

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.simple_count)
    private TextView simpleCount;

    @ViewInject(R.id.rb_receive_1)
    private RadioButton rbReceive1;

    @ViewInject(R.id.rb_receive_2)
    private RadioButton rbReceive2;

    @ViewInject(R.id.group_receive)
    private RadioGroup rGreceive;

    @ViewInject(R.id.price)
    private TextView price;

    @ViewInject(R.id.rv_select_reason)
    private RecyclerView rvSelectReason;

    @ViewInject(R.id.rv_refund_photo)
    private RecyclerView rvRefundPhoto;

    @ViewInject(R.id.sign_txt)
    private EditText signTxt;

    @ViewInject(R.id.sign_count)
    private TextView signCount;

    @ViewInject(R.id.reason)
    private TextView reason;

    @Event(R.id.reason_layout)
    private void reasonLayoutClick(View view) {
        rvSelectReason.setVisibility(View.VISIBLE);
    }

    @ViewInject(R.id.layout_big_photo)
    private RelativeLayout photoLayout;

    @Event(R.id.take_photo)
    private void takePhotoClick(View view) {
        takePhoto();
        photoLayout.setVisibility(View.GONE);
    }

    @Event(R.id.select_photo)
    private void selectPhoto(View view) {
        choosePhoto();
        photoLayout.setVisibility(View.GONE);
    }

    @Event(R.id.layout_big_photo)
    private void layoutAllPhoto(View view) {
        photoLayout.setVisibility(View.GONE);
    }

    @Event(R.id.iv_reduce)
    private void reduce(View view) {
        if (currentCount == 0) {
            return;
        }

        currentCount = currentCount - 1;
        getRefundChangeNum(currentCount);
//        simpleCount.setText(currentCount);
    }

    @Event(R.id.iv_add)
    private void add(View view) {
        if (currentCount == maxCount) {
            T.s("已超过最大退款数");
            return;
        }
        currentCount = currentCount + 1;
//        simpleCount.setText(currentCount);
        getRefundChangeNum(currentCount);
    }

    @Event(R.id.commit)
    private void commit(View view) {
        commitRefund();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();

        Intent intent = getIntent();
        yuyueId = intent.getStringExtra("yuyueId");

        getRefundData();
    }

    private void getRefundData() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PREREFUNDMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUECARD_ID", yuyueId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PreRefundMyYuyueResponse response = gson.fromJson(result, PreRefundMyYuyueResponse.class);
                switch (response.getResult()) {
                    case 0:
                        bean = response.getData();
                        updateView();
                        break;
                    default:
                        finish();
                        T.s("获取申请退款信息失败");
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

    private void getRefundChangeNum(final int num) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PREREFUNDNUM);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUECARD_ID", yuyueId);
        params.addQueryStringParameter("NUM", num);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                PreRefundNumResponse response = gson.fromJson(result, PreRefundNumResponse.class);
                switch (response.getResult()) {
                    case 0:
                        simpleCount.setText(response.getData().getNUM());
                        price.setText(response.getData().getREFUNDAMT());
                        break;
                    default:
                        T.s("订单目前不可部分退款");
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

    private void commitRefund() {
        if (simpleCount.getText().toString().equals("0")) {
            T.s("退款件数不能为0");
            return;
        }
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_REFUNDMYYUYUE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("YUYUECARD_ID", yuyueId);
        params.addQueryStringParameter("NUM", simpleCount.getText().toString());
        params.addQueryStringParameter("HAS_RECEIVE", receiveType);
        params.addQueryStringParameter("REFUND_INFO", reason.getText().toString());
        params.addQueryStringParameter("REFUND_DETAIL", signTxt.getText().toString());
        params.addQueryStringParameter("REFUND_PIC", null);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        finish();
                        T.s("提交退款申请成功");
                        break;
                    default:
                        finish();
                        T.s("提交退款申请失败");
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

    private void updateView() {
        time.setText(bean.getCREATETIME());
        if (!TextUtils.isEmpty(bean.getNUM())) {
            maxCount = Integer.parseInt(bean.getNUM());
        }
        price.setText("￥" + bean.getREFUNDAMT());
        simpleCount.setText(bean.getNUM());
    }

    private void initView() {
        rGreceive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_receive_1:
                        receiveType = 1;
                        break;
                    case R.id.rb_receive_2:
                        receiveType = 2;
                        break;
                }
            }
        });

        signTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                signCount.setText(i2 + "/100");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        reasonList = new ArrayList<>();
        reasonList.add("多拍/拍错/不想要");
        reasonList.add("空包裹");
        reasonList.add("未按规定时间发货");
        reasonList.add("物流一直未送达");
        reasonList.add("其他");

        reasonAdapter = new ReasonAdapter(reasonList);
        reasonAdapter.setOnItemClickListener(new ReasonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                rvSelectReason.setVisibility(View.GONE);
                reason.setText(reasonList.get(position));
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSelectReason.setAdapter(reasonAdapter);
        rvSelectReason.setLayoutManager(manager);

        photoList = new ArrayList<>();
        photoList.add("");

        photoAdapter = new RefundPhotoAdapter(photoList, this);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvRefundPhoto.setAdapter(photoAdapter);
        rvRefundPhoto.setLayoutManager(manager1);
    }

    public void addPhoto() {
        photoLayout.setVisibility(View.VISIBLE);
    }

    public void deletePhoto(int position) {
        photoList.remove(position);
        photoAdapter.notifyDataSetChanged();
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                Uri uri = data.getData();
                String filePath = FileUtil.getFilePathByUri(this, uri);
                if (!TextUtils.isEmpty(filePath)) {
                    photoList.add(filePath);
                    photoAdapter.notifyDataSetChanged();
                } else {
                    T.s("选择照片出错");
                }
                break;
            case RC_TAKE_PHOTO:
                if (!TextUtils.isEmpty(mTempPhotoPath)) {
                    photoList.add(mTempPhotoPath);
                    photoAdapter.notifyDataSetChanged();
                } else {
                    T.s("选择照片出错");
                }
                break;
        }
    }

    private void takePhoto() {
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "photoTest" + File.separator);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File photoFile = new File(fileDir, "photo.jpeg");
        mTempPhotoPath = photoFile.getAbsolutePath();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            /*7.0以上要通过FileProvider将File转化为Uri*/
            imageUri = FileProvider.getUriForFile(this, "", photoFile);
        } else {
            /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
            imageUri = Uri.fromFile(photoFile);
        }
        System.out.println(imageUri);
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, RC_TAKE_PHOTO);
    }
}
