package com.babyraising.aipaperurine.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login_third)
public class LoginThirdActivity extends BaseActivity {

    @ViewInject(R.id.phone)
    private EditText phone;

    @ViewInject(R.id.code)
    private EditText code;

    @ViewInject(R.id.send_code)
    private Button sendCode;

    @Event(R.id.send_code)
    private void sendCodeClick(View view) {

    }

    @Event(R.id.login)
    private void loginClick(View view) {

    }

    @Event(R.id.register_layout)
    private void registerLayoutClick(View view) {

    }

    @Event(R.id.bind_phone)
    private void bindPhoneClick(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
