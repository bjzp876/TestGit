package com.bm.test.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.test.activity.BaseActivity;
import com.bm.test.R;
import com.bm.test.activity.main.MainActivity;

/**
 * 登陆页
 * Created by zhangp01 on 2017/3/2/002.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    //忘记密码
    private TextView tv_login_forget_passwords;
    //返回
    private LinearLayout search_leftLayout;
    //标题
    private TextView search_titleText;
    //登陆
    private Button btn_login_login;

    @Override
    public void initView() {
        setContentLayout(R.layout.ac_login);
    }

    @Override
    public void init() {
        tv_login_forget_passwords = (TextView) findViewById(R.id.tv_login_forget_passwords);
        search_leftLayout = (LinearLayout) findViewById(R.id.search_leftLayout);
        search_titleText = (TextView) findViewById(R.id.search_titleText);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
    }

    @Override
    public void setData() {
        search_leftLayout.setOnClickListener(this);
        tv_login_forget_passwords.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        search_titleText.setText("登陆");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_forget_passwords:
                mIntent = new Intent(mContext,ForgetActivity.class);
                startActivity(mIntent);
                break;
            case R.id.search_leftLayout:
                finish();
                break;
            case R.id.btn_login_login:
                mIntent = new Intent(mContext, MainActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
    }
}
