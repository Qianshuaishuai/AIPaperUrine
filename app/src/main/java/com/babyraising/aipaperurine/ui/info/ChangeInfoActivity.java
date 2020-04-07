package com.babyraising.aipaperurine.ui.info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.PersonBean;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.CouponResponse;
import com.babyraising.aipaperurine.response.EditImgResponse;
import com.babyraising.aipaperurine.response.EditUserResponse;
import com.babyraising.aipaperurine.ui.person.ChangeSignActivity;
import com.babyraising.aipaperurine.ui.person.MailEditActivity;
import com.babyraising.aipaperurine.ui.person.SignActivity;
import com.babyraising.aipaperurine.util.FileUtil;
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

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import cn.aigestudio.datepicker.views.DatePicker;

@ContentView(R.layout.activity_change_info)
public class ChangeInfoActivity extends BaseActivity {

    private UserBean userBean;
    private PersonBean personBean;

    public static final int RC_TAKE_PHOTO = 1;
    public static final int RC_CHOOSE_PHOTO = 2;
    public static final int SIGN_CODE = 101;

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

    @ViewInject(R.id.card_city)
    private TextView cardCity;

    @ViewInject(R.id.card_email)
    private TextView cardEmail;

    @ViewInject(R.id.card_sign)
    private TextView cardSign;

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

    @Event(R.id.layout_sign)
    private void layoutSignClick(View view) {
        Intent intent = new Intent(this, ChangeSignActivity.class);
        startActivityForResult(intent, SIGN_CODE);
    }

    @Event(R.id.layout_city)
    private void layoutCityClick(View view) {

    }

    @Event(R.id.commit)
    private void commit(View view) {
        editUser(userBean.getNICKNAME(), userBean.getSEX(), userBean.getCITY(), userBean.getPROVICE_CODE(), userBean.getCITY_CODE(), userBean.getDISTRICT_CODE(), userBean.getBIRTHDAY(), userBean.getSIGNNAME());
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

        ImageOptions options = new ImageOptions.Builder().
                setRadius(DensityUtil.dip2px(50)).build();
        x.image().bind(cardIcon, userBean.getHEADIMG(), options);

        cardName.setText(userBean.getNICKNAME());
        cardDate.setText(userBean.getBIRTHDAY());
        if (userBean.getSEX().equals("2")) {
            bt1Sex.setChecked(false);
            bt2Sex.setChecked(true);
        } else if (userBean.getSEX().equals("1")) {
            bt1Sex.setChecked(true);
            bt2Sex.setChecked(false);
        }

        cardCity.setText(userBean.getCITY());
        cardSign.setText(userBean.getSIGNNAME());
        cardEmail.setText(userBean.getEMAIL());

        if (TextUtils.isEmpty(userBean.getEMAIL())) {
            cardEmail.setText("未绑定");
        }

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.bt1_sex:
//                        editUser("", "1", "", "", "", "", "", "");
                        userBean.setSEX("1");
                        break;

                    case R.id.bt2_sex:
//                        editUser("", "2", "", "", "", "", "", "");
                        userBean.setSEX("2");
                        break;
                }
            }
        });

        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userBean.setNICKNAME(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                        String result = year + "-" + month + "-" + dayOfMonth;
                        cardDate.setText(result);
                        if (layoutDatePicker.getVisibility() == View.VISIBLE) {
                            layoutDatePicker.setVisibility(View.GONE);
                        }
                        userBean.setBIRTHDAY(result);
                    }
                },
                mYear, mMonth, mDay);
    }

    private void editUser(String nickName, String sex, String city, String provinceCode, String cityCode, String ditCode, String birthday, String signName) {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITUSER);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());

        if (!TextUtils.isEmpty(nickName)) {
            params.addQueryStringParameter("NICKNAME", nickName);
        }

        if (!TextUtils.isEmpty(sex)) {
            params.addQueryStringParameter("SEX", sex);
        }

        if (!TextUtils.isEmpty(city)) {
            params.addQueryStringParameter("CITY", city);
            params.addQueryStringParameter("PROVICE_CODE", provinceCode);
            params.addQueryStringParameter("CITY_CODE", cityCode);
            params.addQueryStringParameter("DISTRICT_CODE", ditCode);
        }

        if (!TextUtils.isEmpty(birthday)) {
            params.addQueryStringParameter("BIRTHDAY", birthday);
        }

        if (!TextUtils.isEmpty(signName)) {
            params.addQueryStringParameter("SIGNNAME", signName);
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
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_EDITIMG);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        File picFile = new File(pic);
        params.addQueryStringParameter("HEADIMG", picFile);
//        params.addBodyParameter("HEADIMG", new File(pic),"multipart/form-data");
//        params.addQueryStringParameter("HEADIMG", pic);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                EditImgResponse response = gson.fromJson(result, EditImgResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("更换成功");
                        userBean.setHEADIMG(response.getData().getHEADIMG());
                        ((PaperUrineApplication) getApplication()).saveUserInfo(userBean);


                        ImageOptions options = new ImageOptions.Builder().
                                setRadius(DensityUtil.dip2px(50)).build();
                        x.image().bind(cardIcon, userBean.getHEADIMG(), options);
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
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
                    ImageOptions options = new ImageOptions.Builder().
                            setRadius(DensityUtil.dip2px(50)).build();
                    x.image().bind(cardIcon, mTempPhotoPath, options);
                    updateLoadPic(mTempPhotoPath);
                } else {
                    T.s("选择照片出错");
                }
                break;

            case SIGN_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
//                        editUser("", "", "", "", "", "", "", data.getStringExtra("sign"));
                        cardSign.setText(data.getStringExtra("sign"));
                        userBean.setSIGNNAME(data.getStringExtra("sign"));
                    }
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
