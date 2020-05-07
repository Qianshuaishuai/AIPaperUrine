package com.xinxin.aicare.ui.info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinxin.aicare.Constant;
import com.xinxin.aicare.PaperUrineApplication;
import com.xinxin.aicare.R;
import com.xinxin.aicare.base.BaseActivity;
import com.xinxin.aicare.bean.MemberInfoBean;
import com.xinxin.aicare.bean.MemberListBean;
import com.xinxin.aicare.bean.PersonBean;
import com.xinxin.aicare.bean.UserBean;
import com.xinxin.aicare.response.CommonResponse;
import com.xinxin.aicare.response.EditImgResponse;
import com.xinxin.aicare.response.EditUserResponse;
import com.xinxin.aicare.response.MemberInfoResponse;
import com.xinxin.aicare.ui.person.MailEditActivity;
import com.xinxin.aicare.util.FileUtil;
import com.xinxin.aicare.util.T;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.aigestudio.datepicker.views.DatePicker;

@ContentView(R.layout.activity_change_baby_info)
public class ChangeBabyInfoActivity extends BaseActivity {

    private UserBean userBean;
    private PersonBean personBean;
    private MemberListBean memberBean;
    private MemberInfoBean memberInfoBean;

    public static final int RC_TAKE_PHOTO = 1;
    public static final int RC_CHOOSE_PHOTO = 2;

    private String mTempPhotoPath;
    private Uri imageUri;

    private DatePickerDialog yearMonthDatePickerDialog;

    @ViewInject(R.id.date_picker)
    private DatePicker datePicker;

    @ViewInject(R.id.layout_date_picker)
    private RelativeLayout layoutDatePicker;

    @ViewInject(R.id.card_date)
    private TextView cardDate;

    @ViewInject(R.id.card_icon)
    private ImageView cardIcon;

    @ViewInject(R.id.card_name)
    private EditText cardName;

    @ViewInject(R.id.group_sex)
    private RadioGroup sexGroup;

    @ViewInject(R.id.bt1_sex)
    private RadioButton bt1Sex;

    @ViewInject(R.id.bt2_sex)
    private RadioButton bt2Sex;

    @ViewInject(R.id.card_baby_id)
    private TextView cardBabyID;

    @ViewInject(R.id.card_size)
    private TextView cardSize;

    @ViewInject(R.id.layout_big_photo)
    private RelativeLayout photoLayout;

    @Event(R.id.layout_back_top)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.layout_date)
    private void layoutDataClick(View view) {
//        layoutDatePicker.setVisibility(View.VISIBLE);
        yearMonthDatePickerDialog.show();
    }

    @Event(R.id.layout_email)
    private void layoutEmailClick(View view) {
        Intent intent = new Intent(this, MailEditActivity.class);
        startActivity(intent);
    }

    @Event(R.id.layout_header)
    private void layoutHeaderClick(View view) {
        photoLayout.setVisibility(View.VISIBLE);
    }

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

    @Event(R.id.layout_size)
    private void layoutCityClick(View view) {
        Intent intent = new Intent(this, PickSizeActivity.class);
        startActivityForResult(intent, 10001);
    }

    @Event(R.id.commit)
    private void commit(View view) {
        memberInfoBean.setNICKNAME(cardName.getText().toString());
        editUser(memberInfoBean.getNICKNAME(), memberInfoBean.getSEX(), memberInfoBean.getBIRTHDAY(), memberInfoBean.getDIAPER_BRAND(), memberInfoBean.getDIAPER_SIZE());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatePicker();
        initData();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
        personBean = ((PaperUrineApplication) getApplication()).getPersonInfo();

        Intent intent = getIntent();
        Gson gson = new Gson();
        memberBean = gson.fromJson(intent.getStringExtra("memberBean"), MemberListBean.class);

        if (TextUtils.isEmpty(memberBean.getMEMBER_ID())) {
            T.s("未获取到宝宝id");
            finish();
            return;
        }

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.bt1_sex:
//                        editUser("", "1", "", "", "", "", "", "");
                        memberInfoBean.setSEX("1");
                        break;

                    case R.id.bt2_sex:
//                        editUser("", "2", "", "", "", "", "", "");
                        memberInfoBean.setSEX("2");
                        break;
                }
            }
        });

        getMemberData();
    }

    private void getMemberData() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_MEMBERINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberBean.getMEMBER_ID());
//        params.addBodyParameter("HEADIMG", new File(pic),"multipart/form-data");
//        params.addQueryStringParameter("HEADIMG", pic);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MemberInfoResponse response = gson.fromJson(result, MemberInfoResponse.class);
                switch (response.getResult()) {
                    case 0:
                        memberInfoBean = response.getData();
                        cardBabyID.setText(response.getData().getMEMBER_ID());
                        cardName.setText(response.getData().getNICKNAME());
                        cardDate.setText(response.getData().getBIRTHDAY());
                        cardSize.setText(response.getData().getDIAPER_SIZE());

                        ImageOptions options = new ImageOptions.Builder().
                                setRadius(DensityUtil.dip2px(50)).setCrop(true).build();
                        x.image().bind(cardIcon, memberInfoBean.getHEADIMG(), options);

                        if (memberInfoBean.getSEX().equals("2")) {
                            bt1Sex.setChecked(false);
                            bt2Sex.setChecked(true);
                        } else if (memberInfoBean.getSEX().equals("1")) {
                            bt1Sex.setChecked(true);
                            bt2Sex.setChecked(false);
                        }

                        break;
                    default:
                        T.s("获取宝宝信息失败");
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

    private void editUser(String nickName, String sex, String birthday, String diaperBrand, String diaperSize) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDIT_MEMBERINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberBean.getMEMBER_ID());

        if (!TextUtils.isEmpty(nickName)) {
            params.addQueryStringParameter("NICKNAME", nickName);
        }

        if (!TextUtils.isEmpty(sex)) {
            params.addQueryStringParameter("SEX", sex);
        }

        if (!TextUtils.isEmpty(birthday)) {
            params.addQueryStringParameter("BIRTHDAY", birthday);
        }

        if (!TextUtils.isEmpty(diaperBrand)) {
            params.addQueryStringParameter("DIAPER_BRAND", diaperBrand);
        }

        if (!TextUtils.isEmpty(diaperSize)) {
            params.addQueryStringParameter("DIAPER_SIZE", diaperSize);
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                EditUserResponse response = gson.fromJson(result, EditUserResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("修改成功");
                        finish();
                        ((PaperUrineApplication) getApplication()).saveUserInfo(userBean);
                        break;
                    default:
                        T.s("修改失败");
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

    private void updateLoadPic(String pic) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITMEMBERIMG);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberBean.getMEMBER_ID());
//        File picFile = new File(pic);
//        params.addQueryStringParameter("HEADIMG", picFile);
//        params.addBodyParameter("HEADIMG", new File(pic),"multipart/form-data");
//        params.addQueryStringParameter("HEADIMG", pic);
        File oldFile = new File(pic);
        File newFile = new CompressHelper.Builder(this)
                .setMaxWidth(100)  // 默认最大宽度为720
                .setMaxHeight(100) // 默认最大高度为960
                .setQuality(80)    // 默认压缩质量为80
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(oldFile);
        params.setAsJsonContent(true);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("HEADIMG", newFile));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                EditImgResponse response = gson.fromJson(result, EditImgResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("更换成功");
                        ImageOptions options = new ImageOptions.Builder().
                                setRadius(DensityUtil.dip2px(50)).setCrop(true).build();
                        x.image().bind(cardIcon, response.getData().getHEADIMG(), options);
                        break;
                    default:
                        T.s("更换失败");
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

    private void editMemberBrand(String diaperBrand, String diaperSize) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITMEMBERSIZE);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("MEMBER_ID", memberBean.getMEMBER_ID());
        params.addQueryStringParameter("DIAPER_BRAND", diaperBrand);
        params.addQueryStringParameter("DIAPER_SIZE", diaperSize);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonResponse response = gson.fromJson(result, CommonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("更换尺码成功");
                        break;
                    default:
                        T.s("更换尺码失败");
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
                    updateLoadPic(filePath);
                } else {
                    T.s("选择照片出错");
                }
                break;
            case RC_TAKE_PHOTO:
                if (!TextUtils.isEmpty(mTempPhotoPath)) {
                    updateLoadPic(mTempPhotoPath);
                } else {
                    T.s("选择照片出错");
                }
                break;

            case 10001:
                String brandSize = data.getStringExtra("brandSize");
                String brand = data.getStringExtra("brand");
                String brandId = data.getStringExtra("brandId");

                cardSize.setText(brandSize);
                editMemberBrand(brandId, brandSize);
                memberInfoBean.setDIAPER_BRAND(brandId);
                memberInfoBean.setDIAPER_SIZE(brandSize);
                break;
        }
    }

    private void initDatePicker() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);

        yearMonthDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        String result = year + "-" + (month + 1) + "-" + dayOfMonth;
                        cardDate.setText(result);
                        if (layoutDatePicker.getVisibility() == View.VISIBLE) {
                            layoutDatePicker.setVisibility(View.GONE);
                        }
                        memberInfoBean.setBIRTHDAY(result);
                    }
                },
                mYear, mMonth, mDay);
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
