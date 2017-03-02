package com.bm.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 * @ClassName: AppManager
 * @Description: TODO
 * @author zhaoW
 * 
 */
public class AppManager {
	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 
	 * @Title: getAppManager
	 * @Description: TODO
	 * @param @return
	 * @return AppManager
	 * @throws
	 */
	public static synchronized AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public boolean isHanveActivity(Class<?> cls){
		boolean flag = false;
		for(int i=0;i<activityStack.size();i++){
			if(activityStack.get(i).getClass().equals(cls)){
				flag = true;
			}
		}
			return flag;

	}

	/**
	 * 
	 * @Title: addActivity
	 * @Description: TODO
	 * @param @param activity
	 * @return void
	 * @throws
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 
	 * @Title: currentActivity
	 * @Description: TODO
	 * @param @return
	 * @return Activity
	 * @throws
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 
	 * @Title: finishActivity
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 
	 * @Title: finishActivity
	 * @Description: TODO
	 * @param @param activity
	 * @return void
	 * @throws
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 
	 * @Title: finishActivity
	 * @Description: TODO
	 * @param @param cls
	 * @return void
	 * @throws
	 */
	public void finishActivity(Class<?> cls) {
		for(int i=0;i<activityStack.size();i++){
			Activity activity =activityStack.get(i);
			try {
				if (activity.getClass()!=null&&activity.getClass().equals(cls)) {
					finishActivity(activity);
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		
	}


	/**
	 * 结束所有Activity保留登录界面
	 */
	public void finishAllActivityExcludeLogin() {
		ArrayList<Activity> activityList = new ArrayList<Activity>(
				activityStack);
		for (int i = 0, size = activityList.size(); i < size; i++) {
			Activity activity = activityList.get(i);
			if (null != activityList.get(i)) {
				if(!activityList.get(i).getClass().getSimpleName().equals("LoginActivity")) {
					activityList.get(i).finish();
					activityStack.remove(activity);
				}
			}
		}
	}


	/**
	 * 结束所有Activity保留登主界面
	 */
	public void finishAllActivityExcludeMain() {
		ArrayList<Activity> activityList = new ArrayList<Activity>(
				activityStack);
		for (int i = 0, size = activityList.size(); i < size; i++) {
			Activity activity = activityList.get(i);
			if (null != activityList.get(i)) {
				if(!activityList.get(i).getClass().getSimpleName().equals("MainActivity")) {
					activityList.get(i).finish();
					activityStack.remove(activity);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: finishAllActivity
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 
	 * @Title: getCount
	 * @Description: TODO
	 * @param @return
	 * @return Integer
	 * @throws
	 */
	public Integer getCount() {
		int count = 0;
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		count = activityStack.size();
		return count;
	}

	/**
	 * 
	 * @Title: AppExit
	 * @Description: TODO
	 * @param @param context
	 * @return void
	 * @throws
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}