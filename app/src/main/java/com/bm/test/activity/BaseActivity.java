package com.bm.test.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.test.AppManager;
import com.bm.test.R;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;

public abstract class BaseActivity extends FragmentActivity {

    public Activity activity;
    ProgressDialog dialog;
    public ProgressDialog downloadDialog;
    public Context mContext;
    public Intent mIntent;
    public String phoneNum;
    public FinalHttp fh;
    public Gson gson = new Gson();
    /**
     * 布局内容
     **/
    private LinearLayout ll_base_content = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fh = new FinalHttp();
        fh.configTimeout(15*1000);
        mContext = this;
        setImmersionStatus();
        setupView();
        AppManager.getAppManager().addActivity(this);
        initView();
        init();
        setData();
    }

    /**
     * 界面初始化
     */
    public abstract void initView();


    /**
     * 控件初始化
     */
    public abstract void init();

    /**
     * 数据及点击事件初始化
     */
    public abstract void setData();


    /**
     * 设置全屏
     */
    public void setFullScreen() {
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }


    /**
     * 非阻塞提示方式
     */
    public void prompt(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置沉浸式状态栏
     */
    public void setImmersionStatus() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /**
     * 替换内容布局
     */
    public void setContentLayout(int layoutResID) {
        ll_base_content.removeAllViews();
        View.inflate(this, layoutResID, ll_base_content);
        onContentChanged();
    }


    /**
     * 设置View
     */
    private void setupView() {
        setContentView(R.layout.ac_base);
        ll_base_content = (LinearLayout) findViewById(R.id.ll_base_content);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.xml_null);
    }
}
