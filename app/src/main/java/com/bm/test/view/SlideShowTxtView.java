package com.bm.test.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.bm.test.R;
import com.bumptech.glide.Glide;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SlideShowTxtView extends FrameLayout implements AdapterView.OnItemClickListener{

	// 轮播图图片数量
	private final static int IMAGE_COUNT = 5;
	// 自动轮播的时间间隔
	private final static int TIME_INTERVAL = 5;
	// 自动轮播启用开关
	private final static boolean isAutoPlay = true;

	// 自定义轮播图的资源ID
	private int[] imagesResIds;
	// 放轮播图片的ImageView 的list
	private List<View> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	//传输Intent
	private Intent intent;
	//接收context
	private Context mContext;
	//接收的类型
	private int num;
	//接收的内容
	private Object object;
	FixedSpeedScroller mScroller = null;
	private MyPagerAdapter adapter;
	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public SlideShowTxtView(Context context) {
		this(context, null);

	}

	public SlideShowTxtView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideShowTxtView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData();
		// initUI(context);
		// if (isAutoPlay) {
		// startPlay();
		// }
		// initUI(context);
		// OnItemClickListener(context, imageViewsList);
	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 初始化相关Data
	 */
	private void initData() {
		imageViewsList = new ArrayList<View>();
		dotViewsList = new ArrayList<View>();
	}

	List<String> picurls;
	boolean b = true;

	/**
	 * (内含)启动轮播图
	 * 
	 * @param context
	 * @param picurls
	 *            图片的地址
	 */
	public void seturls(Context context, List<String> picurls, List<String> txtString) {
		this.picurls = picurls;
		if (b) {
			// TODO Auto-generated method stub
			LayoutInflater.from(context).inflate(R.layout.imageswitch, this,
					true);

			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dots_layout);
			for (int i = 0; i < picurls.size(); i++) {
				View view1 = new View(context);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						10, 10);
				layoutParams.setMargins(10, 0, 10, 0);
				view1.setLayoutParams(layoutParams);
				linearLayout.addView(view1);
				dotViewsList.add(view1);
			}

			AbImageLoader mAbImageLoader = AbImageLoader.newInstance(context);
			for (int i=0; i< picurls.size();i++) {
				String imageID=picurls.get(i);
				RelativeLayout relativeLayout = new RelativeLayout(context);
				RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.FILL_PARENT);
				RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				
				ImageView view = new ImageView(context);
				mAbImageLoader.setLoadingImage(R.mipmap.lunbotu_init);
			    mAbImageLoader.setErrorImage(R.mipmap.lunbotu_init);
			    mAbImageLoader.setEmptyImage(R.mipmap.lunbotu_init);
				Glide.with(context).load(imageID).into(view);
				view.setScaleType(ScaleType.FIT_XY);
				relativeLayout.addView(view,lp0);
				
				TextView textView = new TextView(context);
//				textView.setBackgroundResource(R.mipmap.backtouming);;
				textView.setGravity(Gravity.CENTER_VERTICAL);
				textView.setTextColor(Color.WHITE);
				textView.setTextSize(12);
				textView.setPadding(30, 0, 0, 0);
				textView.setText(txtString.get(i));
				textView.setLines(1);
				relativeLayout.addView(textView,lp1);
				
				imageViewsList.add(relativeLayout);
			}

			viewPager = (ViewPager) findViewById(R.id.viewPager);
			controlViewPagerSpeed(viewPager);
			viewPager.setFocusable(true);
			adapter = new MyPagerAdapter();
			viewPager.setAdapter(adapter);
			viewPager.setOnPageChangeListener(new MyPageChangeListener());
			//viewpager的点击事件处理s
			if (isAutoPlay) {
				startPlay();
			}
			b = false;
		}
	}

	/**
	 * 修改轮播图速度
	 * @param ViewPager
	 */
	private void controlViewPagerSpeed(ViewPager ViewPager) {
		try {
			Field mField;

			mField = android.support.v4.view.ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);

			mScroller = new FixedSpeedScroller(
					ViewPager.getContext(),
					new AccelerateInterpolator());
			mScroller.setmDuration(800); // 2000ms
			mField.set(ViewPager, mScroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填充ViewPager的页面适配器
	 * 
	 * @author zw
	 */
	private class MyPagerAdapter extends PagerAdapter {

		public MyPagerAdapter(){

		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			((ViewPager) container).addView(imageViewsList.get(position));
//			View view = imageViewsList.get(position);
//			view.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					switch (num) {
//					case 1:
//						List<LunBoTuBean> list = new ArrayList<LunBoTuBean>();
//						list = (List<LunBoTuBean>) object;
//						LunBoTuBean bean = list.get(position);
//						int type = bean.getCarouselFigureType();
//						int carouselFigureId = bean.getCarouselFigureId();
//						intent.putExtra("type", String.valueOf(type));
//						intent.putExtra("carouselFigureId", String.valueOf(carouselFigureId));
//						break;
//					case 2:
//						
//						break;
//					default:
//						break;
//					}
//					mContext.startActivity(intent);
//				}
//			});
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author zw
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
					mScroller.setmDuration(400); // 2000ms.
					viewPager.setCurrentItem(0,false);
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					mScroller.setmDuration(400); // 2000ms.
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}else{
					mScroller.setmDuration(800); // 2000ms.
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@SuppressLint({ "ResourceAsColor", "NewApi" })
		@Override
		public void onPageSelected(int pos) {
			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.dian_p);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.mipmap.dian_p);
				}
			}
		}

	}

	/**
	 * 执行轮播图切换任务
	 * 
	 * @author zw
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}


	
	
//	/**
//	 * 新闻详情点击事件
//	 * @param context
//	 * @param lunBoTuBeans
//	 */
//	public void setOnWebClickListener(final Context context, final List<NewsInfo> lunBoTuBeans) {
//		for (int position = 0; position < imageViewsList.size(); position++) {
//			View imageView = imageViewsList.get(position);
//			final int index = position;
//			imageView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					String url = String.valueOf(lunBoTuBeans.get(index).getWapLink());
//					context.startActivity(new Intent(context,
//							NewsInfoActivity.class)
//							.putExtra("url",url)
//							);
//				}
//			});
//		}
//	}
	


	/**
	 * 销毁ImageView资源，回收内存
	 * 
	 * @author ZhaoW
	 */
	private void destoryBitmaps() {

		 for (int i = 0; i < imageViewsList.size(); i++) {
		 ImageView imageView = (ImageView) imageViewsList.get(i);
		 Drawable drawable = imageView.getDrawable();
		 if (drawable != null) {
		 // 解除drawable对view的引用
		 drawable.setCallback(null);
		 }
		 }
	}

	public interface SetText {
		public void settextss(int positions);
	}

	public void SetOnTextListner(SetText setText) {

	}
	/**
	 * Activity的跳转事件
	 * @param mContext
	 * @param intent
	 */
	public void getActivity(Context mContext, Intent intent, int style, Object object){
		this.mContext = mContext;
		this.intent = intent;
		this.num = style;
		this.object = object;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub
		
	}

}
