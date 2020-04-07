package com.babyraising.aipaperurine.ui.info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.EditImgResponse;
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

@ContentView(R.layout.activity_baby_bind)
public class BabyBindActivity extends BaseActivity {

    private int sexType = 1;
    private UserBean userBean;

    public static final int RC_TAKE_PHOTO = 1;
    public static final int RC_CHOOSE_PHOTO = 2;
    public static final int PICK_SIZE_CODE = 101;

    private String mTempPhotoPath;
    private Uri imageUri;

    private DatePickerDialog yearMonthDatePickerDialog;

    private String brand = "";
    private String brandSize = "";
    private String photoPath = "";

    @ViewInject(R.id.sex_iv1)
    private ImageView sexIv1;

    @ViewInject(R.id.sex_iv2)
    private ImageView sexIv2;

    @ViewInject(R.id.sex_tv1)
    private TextView sexTv1;

    @ViewInject(R.id.sex_tv2)
    private TextView sexTv2;

    @ViewInject(R.id.baby_size)
    private EditText babySize;

    @ViewInject(R.id.baby_name)
    private EditText babyName;

    @ViewInject(R.id.baby_date)
    private EditText babyDate;

    @ViewInject(R.id.card_icon)
    private ImageView cardIcon;

    @ViewInject(R.id.layout_big_photo)
    private RelativeLayout photoLayout;

    @Event(R.id.layout_input_id)
    private void inputLayoutClick(View view) {
        Intent intent = new Intent(this, BabyIdActivity.class);
        startActivity(intent);
    }

    @Event(R.id.layout_header)
    private void layoutHeader(View view) {
        photoLayout.setVisibility(View.VISIBLE);
    }

    @Event(R.id.layout_back)
    private void back(View view) {
        finish();
    }

    @Event(R.id.baby_date)
    private void babyDateClick(View view) {
        yearMonthDatePickerDialog.show();
    }

    @Event(R.id.baby_size)
    private void babySizeClick(View view) {
        Intent intent = new Intent(this, PickSizeActivity.class);
        startActivityForResult(intent, PICK_SIZE_CODE);
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

    @Event(R.id.sex_iv1)
    private void sexIv1(View view) {
        if (sexType != 1) {
            sexType = 1;
            sexIv1.setImageResource(R.mipmap.img_nanbaobao_liang);
            sexTv1.setTextColor(getResources().getColor(R.color.black));
            sexIv2.setImageResource(R.mipmap.img_nvbaobao_an);
            sexTv2.setTextColor(getResources().getColor(R.color.grey));
        }
    }

    @Event(R.id.sex_iv2)
    private void sexIv2(View view) {
        if (sexType != 2) {
            sexType = 2;
            sexIv1.setImageResource(R.mipmap.img_nanbaobao_an);
            sexTv1.setTextColor(getResources().getColor(R.color.grey));
            sexIv2.setImageResource(R.mipmap.img_nvbaobao_liang);
            sexTv2.setTextColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initDatePicker();
    }

    private void initData() {
        userBean = ((PaperUrineApplication) getApplication()).getUserInfo();
    }

    private void initView() {

    }

    private void addMember() {

        if (babyName.getText().toString().length() == 0) {
            T.s("宝宝名字不能为空");
            return;
        }

        if (TextUtils.isEmpty(photoPath)) {
            T.s("请先设置宝宝头像");
            return;
        }

        if (babyName.getText().toString().length() == 0) {
            T.s("宝宝名字不能为空");
            return;
        }

        if (TextUtils.isEmpty(babyDate.getText().toString())) {
            T.s("请先选择宝宝出生日期");
            return;
        }

        if (TextUtils.isEmpty(brand)) {
            T.s("请先选择尿布品牌和尺寸");
            return;
        }


        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_ADDMEMBER);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        params.addQueryStringParameter("HEADIMG", photoPath);
        params.addQueryStringParameter("NICKNAME", babyName.getText().toString());
        params.addQueryStringParameter("SEX", sexType);
        params.addQueryStringParameter("BIRTHDAY", babyDate.getText().toString());
        params.addQueryStringParameter("DIAPER_BRAND", brand);
        params.addQueryStringParameter("DIAPER_SIZE", brandSize);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                EditImgResponse response = gson.fromJson(result, EditImgResponse.class);
                switch (response.getResult()) {
                    case 0:
                        T.s("添加成功");

                        break;
                    default:
                        T.s("添加失败");
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
                        babyDate.setText(result);
                    }
                },
                mYear, mMonth, mDay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                Uri uri = data.getData();
                String filePath = FileUtil.getFilePathByUri(this, uri);
                if (!TextUtils.isEmpty(filePath)) {
                    ImageOptions options = new ImageOptions.Builder().
                            setRadius(DensityUtil.dip2px(84)).build();
                    x.image().bind(cardIcon, filePath, options);
                    photoPath = filePath;
                } else {
                    T.s("选择照片出错");
                }
                break;
            case RC_TAKE_PHOTO:
                if (!TextUtils.isEmpty(mTempPhotoPath)) {
                    ImageOptions options = new ImageOptions.Builder().
                            setRadius(DensityUtil.dip2px(84)).build();
                    x.image().bind(cardIcon, mTempPhotoPath, options);
                    photoPath = mTempPhotoPath;
                } else {
                    T.s("选择照片出错");
                }

                break;

            case PICK_SIZE_CODE:
                brand = data.getStringExtra("brand");
                brandSize = data.getStringExtra("brandSize");
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
