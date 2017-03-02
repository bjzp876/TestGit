package com.bm.test.activity.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.test.activity.BaseActivity;
import com.bm.test.R;
import com.bm.test.view.CountdownButton;

/**
 * 忘记密码
 * Created by zhangp01 on 2017/3/2/002.
 */

public class ForgetActivity extends BaseActivity implements View.OnClickListener{
    //返回
    private LinearLayout search_leftLayout;
    //标题
    private TextView search_titleText;
    //手机号
    private EditText et_loginyzm_phone;
    //验证码
    private EditText et_loginyzm_yzm;
    //获取验证码
    private CountdownButton cbn_forger_password_timer;
    //下一步
    private Button bt_loginyzm;

    @Override
    public void initView() {
        setContentLayout(R.layout.ac_loginyzm);
    }

    @Override
    public void init() {
        search_leftLayout = (LinearLayout)findViewById(R.id.search_leftLayout);
        cbn_forger_password_timer = (CountdownButton)findViewById(R.id.cbn_loginyzm_password_timer);
        search_titleText = (TextView)findViewById(R.id.search_titleText);
        bt_loginyzm = (Button)findViewById(R.id.bt_loginyzm);
        et_loginyzm_phone = (EditText)findViewById(R.id.et_loginyzm_phone);
        et_loginyzm_yzm = (EditText)findViewById(R.id.et_loginyzm_yzm);
    }

    @Override
    public void setData() {
        cbn_forger_password_timer.setLenght(60 * 1000);
        cbn_forger_password_timer.setOnClickListener(this);
        search_leftLayout.setOnClickListener(this);
        bt_loginyzm.setOnClickListener(this);
        search_titleText.setText("忘记密码");
        bt_loginyzm.setText("下一步");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cbn_loginyzm_password_timer:
                if(et_loginyzm_phone.getText().toString() == null||et_loginyzm_phone.getText().toString().equals("")){
                    Toast.makeText(mContext,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(et_loginyzm_phone.getText().toString().length()!=11){
                    Toast.makeText(mContext,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    return ;
                }
                cbn_forger_password_timer.setStart(true);
                break;
            case R.id.search_leftLayout:
                finish();
                break;
            case R.id.bt_loginyzm:
                isSuccess();
                finish();
                break;

        }
    }


    private void isSuccess(){
        if(et_loginyzm_phone.getText().toString() == null||et_loginyzm_phone.getText().toString().equals("")){
            Toast.makeText(mContext,"请输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_loginyzm_yzm.getText().toString() == null||et_loginyzm_yzm.getText().toString().equals("")){
            Toast.makeText(mContext,"请输入验证码",Toast.LENGTH_SHORT).show();
            return ;
        }
    }
}
