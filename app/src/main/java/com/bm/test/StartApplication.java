package com.bm.test;

import android.app.Activity;
import android.app.Application;


import com.bm.test.dao.TreeDBDao;
import com.bm.test.util.CrashHandler;
import com.orhanobut.logger.Logger;
import java.util.LinkedList;
import java.util.List;



/**
 * 初始化application
 * @author zhangp01
 *
 */
public class StartApplication extends Application {
	
	private List<Activity> activityList = new LinkedList<Activity>();
	private static StartApplication instance;
	private TreeDBDao dao;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Logger.init("WZDY");
		instance = this;
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		dao  = new TreeDBDao(getApplicationContext());
		dao.openDataBase();
	}
	
	/**
	 * 单例模式中获取唯一的StartApplication实例 (由于application在项目中其是本身已经是单例了)
	 * 
	 * @return
	 */
	public static StartApplication getInstance() {
		return instance;
	}

	/**
	 * TODO 添加Activity到容器中
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * TODO 遍历所有Activity并finish
	 */
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		// System.exit(0);
	}

	public TreeDBDao getDao(){
		return  dao;
	}

}
