package com.babyraising.aipaperurine.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.phone)
    private EditText phone;

    @ViewInject(R.id.code)
    private EditText code;

    @ViewInject(R.id.password)
    private EditText password;

    @ViewInject(R.id.sure_password)
    private EditText surePassword;

    @ViewInject(R.id.send_code)
    private TextView sendCode;

    @Event(R.id.back)
    private void backClick(View view){

    }

    @Event(R.id.register_login)
    private void rlClick(View view){

    }

    @Event(R.id.send_code)
    private void sendCodeClick(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
