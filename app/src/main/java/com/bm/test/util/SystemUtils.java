package com.bm.test.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SystemUtils {
	 /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /** 
     * 跳转到WIFI设置 
     *  
     * @param context 
     */  
    public static void intentWifiSetting(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 10) {  
            // 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面  
            context.startActivity(new Intent(
                    Settings.ACTION_SETTINGS));
        } else {  
            context.startActivity(new Intent(
                    Settings.ACTION_WIRELESS_SETTINGS));
        }  
    }  
  
    /** 
     * WIFI网络开关 
     *  
     */  
    public static void toggleWiFi(Context context, boolean enabled) {
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        wm.setWifiEnabled(enabled);  
    }  
  
    /** 
     * 移动网络开关 
     */  
    public static void toggleMobileData(Context context, boolean enabled) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> conMgrClass = null; // ConnectivityManager类
        Field iConMgrField = null; // ConnectivityManager类中的字段
        Object iConMgr = null; // IConnectivityManager类的引用
        Class<?> iConMgrClass = null; // IConnectivityManager类
        Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法
        try {  
            // 取得ConnectivityManager类  
            conMgrClass = Class.forName(conMgr.getClass().getName());
            // 取得ConnectivityManager类中的对象mService  
            iConMgrField = conMgrClass.getDeclaredField("mService");  
            // 设置mService可访问  
            iConMgrField.setAccessible(true);  
            // 取得mService的实例化类IConnectivityManager  
            iConMgr = iConMgrField.get(conMgr);  
            // 取得IConnectivityManager类  
            iConMgrClass = Class.forName(iConMgr.getClass().getName());
            // 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法  
            setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(  
                    "setMobileDataEnabled", Boolean.TYPE);
            // 设置setMobileDataEnabled方法可访问  
            setMobileDataEnabledMethod.setAccessible(true);  
            // 调用setMobileDataEnabled方法  
            setMobileDataEnabledMethod.invoke(iConMgr, enabled);  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  
        } catch (SecurityException e) {
            e.printStackTrace();  
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {
            e.printStackTrace();  
        } catch (IllegalAccessException e) {
            e.printStackTrace();  
        } catch (InvocationTargetException e) {
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * GPS开关 当前若关则打开 当前若开则关闭 
     */  
    public static void toggleGPS(Context context) {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings",  
                "com.android.settings.widget.SettingsAppWidgetProvider");  
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");  
        gpsIntent.setData(Uri.parse("custom:3"));
        try {  
            PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 调节系统音量 
     *  
     * @param context 
     */  
    public static void holdSystemAudio(Context context) {
        AudioManager audiomanage = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        // 获取系统最大音量  
        // int maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC);   
        // 获取当前音量  
        // int currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_RING);   
        // 设置音量  
        // audiomanage.setStreamVolume(AudioManager.STREAM_SYSTEM, currentVolume, AudioManager.FLAG_PLAY_SOUND);  
          
        // 调节音量  
        // ADJUST_RAISE 增大音量，与音量键功能相同   
        // ADJUST_LOWER 降低音量  
        audiomanage.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
  
    }  
  
    /** 
     * 设置亮度（每30递增） 
     *  
     */
    public static void setBrightness(Activity activity) {
        ContentResolver resolver = activity.getContentResolver();
        Uri uri = Settings.System
                .getUriFor("screen_brightness");  
        int nowScreenBri = getScreenBrightness(activity);  
        nowScreenBri = nowScreenBri <= 225 ? nowScreenBri + 30 : 30;  
        System.out.println("nowScreenBri==" + nowScreenBri);
        Settings.System.putInt(resolver, "screen_brightness",
                nowScreenBri);  
        resolver.notifyChange(uri, null);  
    }  
  
    /** 
     * 获取屏幕的亮度 
     *  
     * @param activity 
     * @return 
     */  
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;  
        ContentResolver resolver = activity.getContentResolver();
        try {  
            nowBrightnessValue = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return nowBrightnessValue;  
    }  
  
    /** 
     * 跳转到系统设置 
     *  
     * @param context 
     */  
    public static void intentSetting(Context context) {
        String pkg = "com.android.settings";
        String cls = "com.android.settings.Settings";
  
        ComponentName component = new ComponentName(pkg, cls);
        Intent intent = new Intent();
        intent.setComponent(component);  
  
        context.startActivity(intent);  
    }  
      
    /** 
     * 获取文件夹下所有文件 
     * @param path 
     * @return 
     */  
    public static ArrayList<File> getFilesArray(String path){
        File file = new File(path);
        File files[] = file.listFiles();
        ArrayList<File> listFile = new ArrayList<File>();
        if (files != null) {  
            for (int i = 0; i < files.length; i++) {  
                if (files[i].isFile()) {  
                    listFile.add(files[i]);  
                }  
                if (files[i].isDirectory()) {  
                    listFile.addAll(getFilesArray(files[i].toString()));  
                }  
            }  
        }  
        return listFile;  
    }  
      
    /**  
     * 获取视频的缩略图  
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。  
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。  
     * @param videoPath 视频的路径  
     * @param width 指定输出视频缩略图的宽度  
     * @param height 指定输出视频缩略图的高度度  
     * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。  
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96  
     * @return 指定大小的视频缩略图  
     */    
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                           int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图     
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        //System.out.println("w"+bitmap.getWidth());    
        //System.out.println("h"+bitmap.getHeight());    
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;    
    }  
      
    /** 
     * 打开视频文件 
     * @param context 
     * @param file 视频文件 
     */  
    public static void intentVideo(Context context, File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/*";
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, type);  
        context.startActivity(intent);  
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param long2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：米
     */
    public static double Distance(double long1, double lat1, double long2,
                                  double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 调用系统铃声
     * @param context
     */
    public static void Ring(Context context){
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(context, RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
