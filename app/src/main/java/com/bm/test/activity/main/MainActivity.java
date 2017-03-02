package com.bm.test.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bm.test.R;
import com.bm.test.StartApplication;
import com.bm.test.activity.BaseActivity;
import com.bm.test.adapter.ViewPagerAdapter;
import com.bm.test.dao.TreeDBDao;
import com.bm.test.fragment.main.MainFragment;
import com.bm.test.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主页
 * Created by zhangp01 on 2017/3/2/002.
 */

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener{
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    // 底部菜单
    private RadioGroup mTabButtonGroup;
    private RadioButton home_content_rb_0;
    private RadioButton home_content_rb_1;
    private RadioButton home_content_rb_2;
    private RadioButton home_content_rb_3;
    private NoScrollViewPager mTabPager;
    private ViewPagerAdapter adapter;
    private FragmentManager fm;
    private MainFragment myInfoFragment;

    private ArrayList<Fragment> fragments;
    private MainFragment fragment1;
    private MainFragment fragment2;
    private MainFragment fragment3;
    private MainFragment fragment4;

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_main);
    }


    @Override
    public void init() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_content_rg);
        home_content_rb_0 = (RadioButton) findViewById(R.id.home_content_rb_0);
        home_content_rb_1 = (RadioButton) findViewById(R.id.home_content_rb_1);
        home_content_rb_2 = (RadioButton) findViewById(R.id.home_content_rb_2);
        home_content_rb_3 = (RadioButton) findViewById(R.id.home_content_rb_3);
        mTabPager = (NoScrollViewPager) this.findViewById(R.id.tabpager);
    }

    @Override
    public void setData() {
        fragments = new ArrayList<Fragment>();
        MainFragment fragment1 = new MainFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("string","1");
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);

        MainFragment fragment2 = new MainFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("string","2");
        fragment2.setArguments(bundle2);
        fragments.add(fragment2);

        MainFragment fragment3 = new MainFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("string","3");
        fragment3.setArguments(bundle3);
        fragments.add(fragment3);

        MainFragment fragment4 = new MainFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("string","4");
        fragment4.setArguments(bundle4);
        fragments.add(fragment4);

        fm   = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fm,
                fragments);
        mTabPager.setAdapter(adapter);
        mTabPager.setOffscreenPageLimit(4);
        mTabButtonGroup.setOnCheckedChangeListener(this);
        mTabPager.setOnPageChangeListener(this);
        mTabPager.setNoScroll(false);
    }


    /********************************
     * 双击退出
     ***********************************/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                exitBy2Click(); // 调用双击退出函数
                break;
        }
        return false;
    }


    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            TreeDBDao dao = StartApplication.getInstance().getDao();
            dao.closeDataBase();
            System.exit(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                home_content_rb_0.setChecked(true);
                home_content_rb_1.setChecked(false);
                home_content_rb_2.setChecked(false);
                home_content_rb_3.setChecked(false);
                break;
            case 1:
                home_content_rb_0.setChecked(false);
                home_content_rb_1.setChecked(true);
                home_content_rb_2.setChecked(false);
                home_content_rb_3.setChecked(false);
                break;
            case 2:
                home_content_rb_0.setChecked(false);
                home_content_rb_1.setChecked(false);
                home_content_rb_2.setChecked(true);
                home_content_rb_3.setChecked(false);
                break;
            case 3:
                home_content_rb_0.setChecked(false);
                home_content_rb_1.setChecked(false);
                home_content_rb_2.setChecked(false);
                home_content_rb_3.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home_content_rb_0:
                mTabPager.setCurrentItem(0, false);
                break;
            case R.id.home_content_rb_1:
                mTabPager.setCurrentItem(1, false);
                break;
            case R.id.home_content_rb_2:
                mTabPager.setCurrentItem(2, false);
                break;
            case R.id.home_content_rb_3:
                mTabPager.setCurrentItem(3, false);
                break;
            default:
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
