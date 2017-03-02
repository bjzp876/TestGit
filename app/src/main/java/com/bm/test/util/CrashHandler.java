package com.bm.test.util;

import android.content.Context;
import android.os.Environment;

/**
 * 异常崩溃处理
 * Created by zhangp01 on 2016/5/5.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler instance;
    private Context context;
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context ctx) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context = ctx;
    }

    /**
     * 核心方法，当程序crash 会回调此方法， Throwable中存放这错误日志
     */
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {

        String logPath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        }
        arg1.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
