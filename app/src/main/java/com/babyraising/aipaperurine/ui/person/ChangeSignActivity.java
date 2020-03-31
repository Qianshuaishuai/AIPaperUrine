package com.babyraising.aipaperurine.ui.person;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.ui.info.ChangeInfoActivity;
import com.babyraising.aipaperurine.util.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_change_sign)
public class ChangeSignActivity extends BaseActivity {

    @Event(R.id.iv_back)
    private void layoutBack(View view) {
        finish();
    }

    @Event(R.id.save)
    private void save(View view) {

        if (TextUtils.isEmpty(signTxt.getText().toString())) {
            T.s("签名不能为空");
            return;
        }

        Intent intent = new Intent(this, ChangeInfoActivity.class);
        intent.putExtra("sign", signTxt.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @ViewInject(R.id.sign_txt)
    private EditText signTxt;

    @ViewInject(R.id.sign_count)
    private TextView signCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
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
    }
}
