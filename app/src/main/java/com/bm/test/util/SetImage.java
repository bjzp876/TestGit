package com.bm.test.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.test.R;
import com.bm.test.network.BaseInt;
import com.bm.test.network.FileName;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 获取本地图片
 * @author zhangp01
 *
 */
public class SetImage {
	private Activity context;
	private View parent;
	private View v;
	private String name;
	
	
	public SetImage(Activity context, View parent){
		this.context = context;
		this.parent = parent;
	}

    /**
     * 设置头像pop
     *
     * @param v
     */
    public void showPopupWindow(View v, String name) {
    	this.v = v;
    	this.name = name;
        // 一个自定义的布局，作为显示的内容
        View view = LayoutInflater.from(context).inflate(
                R.layout.myinfo_pop_photo, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //拍照
        TextView myinfo_photo = (TextView) view.findViewById(R.id.myinfo_photo);
        myinfo_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                choseHeadImageFromCameraCapture();
            }
        });
        //从相册取照片
        TextView myinfo_album = (TextView) view.findViewById(R.id.myinfo_album);
        myinfo_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                choseHeadImageFromGallery();
            }
        });
        //取消
        TextView popCacel = (TextView) view.findViewById(R.id.myinfo_cancel);
        popCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
    }
    
    
    

    /**
     * 启动手机相机拍摄照片作为头像
     */
    public void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (SystemUtils.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), FileName.IMAGE_FILE_NAME)));
            context.startActivityForResult(intentFromCapture, BaseInt.CODE_CAMERA_REQUEST);
        }
    }

    /**
     * 从本地相册选取图片作为头像
     */
    public void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);
        context.startActivityForResult(intentFromGallery,BaseInt.CODE_GALLERY_REQUEST);
    }
    
    
    
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("name", name);
        context.startActivityForResult(intent, BaseInt.CODE_CUTTING_REQUEST);
    }
    
    
    
    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    public String setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        String url = null;
        MyUtil.createFile(FileName.USER_ICON, context);
        /**
         * 将图片存成本地文件
         */
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");

            // 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray(); // 将图片流以字符串形式存储下来

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            // 例如：cc_time=1291778220
            long lcc_time = System.currentTimeMillis();
            String currentTime = sdf.format(new Date(lcc_time));
            File file = new File(FileName.USER_ICON);
            if(!file .exists()  && !file .isDirectory()){
            	file.mkdir();
            }
            File fileTemp;
            fileTemp = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath(), "/zhdyPic/" + currentTime + ".jpg");
            try {
                if (!fileTemp.exists()) {
                    fileTemp.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(
                        fileTemp);
                fileOutputStream.write(b);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //本地加载头像
             url = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()+"/zhdyPic/"+currentTime+".jpg";
        }
        if(url!=null){
        	return url;
        }else{
        	return null;
        }
    }




}
