package com.bm.test.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.test.R;
import com.bm.test.fragment.BaseFragment;

/**
 * 主页
 * Created by zhangp01 on 2017/3/2/002.
 */

public class MainFragment extends BaseFragment{
    private TextView tv_fg_main;
    private String result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_main,null);
        init(view);
        String result = getArguments().getString("string");
        if(result != null&&!result.equals("")){
            tv_fg_main.setText(result);
        }
        setData();
        return view;
    }

    @Override
    public void init(View view) {
        tv_fg_main = (TextView) view.findViewById(R.id.tv_fg_main);
        String  abc = "123";
    }

    @Override
    public void setData() {


    }
}
