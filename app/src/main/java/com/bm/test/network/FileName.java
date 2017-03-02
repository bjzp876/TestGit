package com.bm.test.network;

import android.os.Environment;

/**
 * 存储文件名
 * Created by zhangp01 on 2016/4/13.
 */
public class FileName {
    public static final String BASEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"";//根目录
    /** 添加设备 */
    public static final String USER_ICON = BASEPATH
            + "/testPic/";
    /* 头像文件 */
    public static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    /* 证书路径 */
    public static final String FILE_NAME = BASEPATH
            + "/testFile/";
}
