package com.wzb.runtimetest;

import com.wzb.runtimetest.util.LogUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 1, 2019 7:02:26 PM	
 */
public class CoreService extends Service{
	
	private Vibrator mVibrator;

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
		
		test_vibrator();
		test_memory();
		test_emmc();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void test_vibrator(){
		if(!WApplication.sp.get("vibrator_s", false)){
			return;
		}
		Intent intent=new Intent();
		intent.setAction("custom.android.vibrator");
		intent.putExtra("status", "testing");
		sendBroadcast(intent);
		startVibrator();
		
	}
	
	private void test_memory(){
		
	}
	
	private void test_emmc(){
		
	}
	
	private void startVibrator() {
		if (mVibrator == null) {
			mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		}
		if (mVibrator != null) {
			
			if(mVibrator.hasVibrator()){
				mVibrator.vibrate( new long[]{1000,2000,1000,2000},0);
				new Handler().postDelayed(new  Runnable() {
					public void run() {
						cancelVibrator();
						Intent intent=new Intent();
						intent.setAction("custom.android.vibrator");
						intent.putExtra("status", "done");
						intent.putExtra("result", "done");
						sendBroadcast(intent);
					}
				}, WApplication.sp_detail.get("vibrator_t", 2400)*1000);
			}else{
				LogUtil.logMessage("wzb", "no vibrator");
			}
		}
	}
	
	private void cancelVibrator() {
		if (mVibrator != null) {
			mVibrator.cancel();
			mVibrator = null;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cancelVibrator();
	}

}
