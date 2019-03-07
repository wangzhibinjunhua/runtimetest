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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 7, 2019 10:04:44 AM
 */
public class BtTest extends BaseActivity {

	private Context mContext;
	TextView tv_bt;
	private BtReceiver mBtReceiver;
	BluetoothAdapter adapter;
	int bt_flag = 0;
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bt);
		mContext = BtTest.this;
		tv_bt = (TextView) findViewById(R.id.tv_bt);
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null)
			bt_flag = 1;
		timer = new Timer();
		TimerTask task_bton1 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		
		TimerTask task_btoff = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(2);
			}
		};
		TimerTask task_bton2 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		
		TimerTask task_btfinish = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(4);
			}
		};
		
		timer.schedule(task_bton1, 1000);
		timer.schedule(task_btoff, 6000);
		timer.schedule(task_bton2, 11000);
		timer.schedule(task_btfinish, 16000);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 1:
				adapter.enable();
				if(bt_flag==2)bt_flag=3;
				break;
			case 2:
				adapter.disable();
				if(bt_flag==1)bt_flag=2;
				break;
			case 3:
				tv_bt.setText((String)msg.obj);
				
				break;
			case 4:
				mHandler.postDelayed(new  Runnable() {
					public void run() {
						if(bt_flag==3){
							WApplication.sp_result.set(WApplication.SPRESULT_S[11], "done");
							WApplication.sp_result.set(WApplication.SPRESULT_R[11], "pass");
						}else{
							WApplication.sp_result.set(WApplication.SPRESULT_S[11], "done");
							WApplication.sp_result.set(WApplication.SPRESULT_R[11], "fail");
						}
						WApplication.sp.set("runin", 4);
						BtTest.this.finish();
					}
				}, 1000);
				break;
			default:
				break;
			}
		};
	};

	public class BtReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			final String action = intent.getAction();
			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
				if (blueState == BluetoothAdapter.STATE_ON) {
						LogUtil.logMessage("wzb", "bt on");
						Message message=mHandler.obtainMessage();
						message.obj="BT: on";
						message.what=3;
						mHandler.sendMessage(message);
				} else if (blueState == BluetoothAdapter.STATE_OFF) {
					LogUtil.logMessage("wzb", "bt off");
					Message message=mHandler.obtainMessage();
					message.obj="BT: off";
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
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		mBtReceiver = new BtReceiver();
		registerReceiver(mBtReceiver, filter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(mBtReceiver);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
	}

}
