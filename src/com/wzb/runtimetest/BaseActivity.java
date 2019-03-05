package com.wzb.runtimetest;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 25, 2019 2:22:08 PM	
 */

public class BaseActivity extends Activity{

	
	public static BaseActivity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		WApplication.activityList.add(this);
		instance = this;
	}
	
	public static class StaticHandler<T> extends Handler {
		public WeakReference<T> mWeakReference;

		public StaticHandler(T t) {
			mWeakReference = new WeakReference<T>(t);
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
