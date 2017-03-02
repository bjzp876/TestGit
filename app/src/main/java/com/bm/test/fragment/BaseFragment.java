package com.bm.test.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;

/**
 * Created by zhangp01 on 2017/3/2/002.
 */

public abstract class BaseFragment extends Fragment{
    public Intent mIntent;
    public Context mContext;
    public FinalHttp fh;
    public Gson gson = new Gson();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        mContext=getActivity();
    }

    /**
     * 控件初始化
     */
    public abstract void init(View view);

    /**
     * 初始化数据
     */
    public abstract void setData();


    /**
     *  初始化Intent
     */
    public void initIntent(){
        mIntent=new Intent();
    }


}
