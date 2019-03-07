package com.wzb.runtimetest.test;

import java.util.Timer;
import java.util.TimerTask;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;
import com.wzb.runtimetest.util.LogUtil;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 7, 2019 10:04:44 AM
 */
public class WifiTest extends BaseActivity {

	private Context mContext;
	TextView tv_wifi;
	private WifiReceiver mWifiReceiver;
	int wifi_flag = 0;
	private Timer timer;
	WifiManager mWifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);
		mContext = WifiTest.this;
		tv_wifi = (TextView) findViewById(R.id.tv_wifi);
		mWifiManager = (WifiManager)   
				mContext.getSystemService(Context.WIFI_SERVICE);
		if(mWifiManager!=null)wifi_flag=1;
		timer = new Timer();
		TimerTask task_wifion1 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		
		TimerTask task_wifioff = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(2);
			}
		};
		TimerTask task_wifion2 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		
		TimerTask task_wififinish = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(4);
			}
		};
		
		timer.schedule(task_wifion1, 1000);
		timer.schedule(task_wifioff, 6000);
		timer.schedule(task_wifion2, 11000);
		timer.schedule(task_wififinish, 16000);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 1:
				mWifiManager.setWifiEnabled(true);
				if(wifi_flag==2)wifi_flag=3;
				break;
			case 2:
				mWifiManager.setWifiEnabled(false);
				if(wifi_flag==1)wifi_flag=2;
				break;
			case 3:
				tv_wifi.setText((String)msg.obj);
				
				break;
			case 4:
				mHandler.postDelayed(new  Runnable() {
					public void run() {
						if(wifi_flag==3){
							WApplication.sp_result.set(WApplication.SPRESULT_S[12], "done");
							WApplication.sp_result.set(WApplication.SPRESULT_R[12], "pass");
						}else{
							WApplication.sp_result.set(WApplication.SPRESULT_S[12], "done");
							WApplication.sp_result.set(WApplication.SPRESULT_R[12], "fail");
						}
						WApplication.sp.set("runin", 5);
						WifiTest.this.finish();
					}
				}, 1000);
				break;
			default:
				break;
			}
		};
	};

	public class WifiReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			final String action = intent.getAction();
			if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
				int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
				if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
						LogUtil.logMessage("wzb", "wifi on");
						Message message=mHandler.obtainMessage();
						message.obj="WIFI: on";
						message.what=3;
						mHandler.sendMessage(message);
				} else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
					LogUtil.logMessage("wzb", "wifi off");
					Message message=mHandler.obtainMessage();
					message.obj="WIFI: off";
					message.what=3;
					mHandler.sendMessage(message);
				}
			}
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		mWifiReceiver = new WifiReceiver();
		registerReceiver(mWifiReceiver, filter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(mWifiReceiver);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
	}

}
