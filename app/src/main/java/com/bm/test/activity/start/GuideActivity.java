package com.bm.test.activity.start;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bm.test.R;
import com.bm.test.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 引导页
 * @author zhangp01
 *
 */
public class GuideActivity extends Activity implements OnPageChangeListener {
	private ViewPager vp;
	private GuideViewPagerAdapter vpAdapter;
	private List<View> views;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_guide);
		initViews();
	}


	@SuppressLint("InflateParams")
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		for (int i = 0; i < 3; i++) {
			if (i == 2) {
				View view = inflater.inflate(R.layout.v_guide_three, null);
				ImageView imageView = (ImageView) view
						.findViewById(R.id.iv_guide_three);
				views.add(view);
			} else if (i == 0) {
				View view = inflater.inflate(R.layout.v_guide_one, null);
				ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide_one);
				views.add(view);
			} else if (i == 1) {
				View view = inflater.inflate(R.layout.v_guide_two, null);
				ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide_two);
				views.add(view);
			}
		}

		vpAdapter = new GuideViewPagerAdapter(views, this);

		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
	}
	
	

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
	}
	

}
