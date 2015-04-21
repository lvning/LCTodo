package com.yiqivr.lctodo;

import android.app.Application;

/**
 * @author lvning
 * @version create time:2015-4-17_下午5:35:26
 * @Description TODO
 */
public class LCApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		LCTodoManager.getInstance().init(this);
	}
}
