package com.wzb.runtimetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 1, 2019 7:02:26 PM	
 */
public class CoreService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
