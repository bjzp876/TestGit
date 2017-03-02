package com.bm.test.activity.start;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bm.test.R;
import com.bm.test.activity.login.LoginActivity;
import com.bm.test.network.FileName;
import com.bm.test.util.MyUtil;
import com.bm.test.util.SettingUtils;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**启动页
 * Created by zhangp01 on 2016/7/20.
 */
public class StartActivity extends Activity {

    boolean isFirstIn = false;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final long SPLASH_DELAY_MILLIS = 3000;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    Gson gson = new Gson();
    private FinalHttp fh;
    private ImageView start_bg;
    private Bitmap btp;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //返回登录页
                case GO_HOME:
//				String LoginURL = Urls.LOGIN;
//				AjaxParams params = new AjaxParams();
//				String phone = SettingUtils.get(WelcomeActivity.this, "phone",
//						"");
//				if (!phone.equals("")) {
//					params.put("phone", phone);
//					LoginUser(LoginURL, params);
//				} else {
//					goHome();
//				}
                    goLogin();
                    break;
                case GO_GUIDE:
                    MyUtil.createFile(FileName.FILE_NAME, StartActivity.this);
//                    createFile();
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ac_start);
        init();
    }


    public void init() {
//		InputStream is = this.getResources().openRawResource(R.drawable.qidongye);
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = false;
//		options.inSampleSize = 2;
//		btp =BitmapFactory.decodeStream(is,null,options);
//		start_bg.setImageBitmap(btp);
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        SettingUtils.set(this,"isLogin",false);
        isFirstIn = SettingUtils.get(this, "isFirstIn", true);
        if (!isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }
    }



    /**
     * 跳转至引导页
     */
    private void goGuide() {
        Intent intent = new Intent(StartActivity.this, GuideActivity.class);
        StartActivity.this.startActivity(intent);
        StartActivity.this.finish();
    }

    private void goLogin() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        StartActivity.this.startActivity(intent);
        StartActivity.this.finish();
    }

    /**
     * 写入证书
     */
    private void createFile(){
        InputStream is = null;
        FileOutputStream fos = null;
        File file = new File(FileName.FILE_NAME+"dasClient.cer");
        if(!file.exists()){
            try {
                file.createNewFile();
                // 打开一个已存在文件的输出流
                fos = new FileOutputStream(file);
                is = StartActivity.this.getAssets().open("dasClient.cer");
                // 将输入流is写入文件输出流fos中
                int ch = 0;
                try {
                    while((ch=is.read()) != -1){
                        fos.write(ch);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally{
                    //关闭输入流等
                    fos.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
