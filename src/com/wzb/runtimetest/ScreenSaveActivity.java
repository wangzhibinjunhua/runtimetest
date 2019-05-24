package com.wzb.runtimetest;

import java.util.Timer;
import java.util.TimerTask;

import com.wzb.runtimetest.util.LogUtil;

import android.app.usage.UsageEvents.Event;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 11, 2019 6:59:36 PM
 */
public class ScreenSaveActivity extends BaseActivity {

	TextView tv1, tv2, tv3, tv4, tv5;
	Timer mTimer;
    private static  String final_result="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screensave);
		initView();
		if(WApplication.sp_result.get("final_result", 0)==3){
			final_result="PASS";
		}else{
			final_result="FAIL";
		}
		initTimer();
	}

	private void initTimer() {
		mTimer = new Timer();
		TimerTask task1 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}

		};
		TimerTask task2 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(2);
			}

		};
		TimerTask task3 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(3);
			}

		};
		TimerTask task4 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(4);
			}

		};
		TimerTask task5 = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(5);
			}

		};
		mTimer.schedule(task1, 0,5000);
		mTimer.schedule(task2, 1000,5000);
		mTimer.schedule(task3, 2000,5000);
		mTimer.schedule(task4, 3000,5000);
		mTimer.schedule(task5, 4000,5000);
	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				tv1.setText(final_result);
				tv1.setVisibility(View.VISIBLE);
				tv2.setVisibility(View.INVISIBLE);
				tv3.setVisibility(View.INVISIBLE);
				tv4.setVisibility(View.INVISIBLE);
				tv5.setVisibility(View.INVISIBLE);
				break;
			case 2:
				tv2.setText(final_result);
				tv2.setVisibility(View.VISIBLE);
				tv1.setVisibility(View.INVISIBLE);
				tv3.setVisibility(View.INVISIBLE);
				tv4.setVisibility(View.INVISIBLE);
				tv5.setVisibility(View.INVISIBLE);
				break;
			case 3:
				tv3.setText(final_result);
				tv3.setVisibility(View.VISIBLE);
				tv1.setVisibility(View.INVISIBLE);
				tv2.setVisibility(View.INVISIBLE);
				tv4.setVisibility(View.INVISIBLE);
				tv5.setVisibility(View.INVISIBLE);
				break;
			case 4:
				tv4.setText(final_result);
				tv4.setVisibility(View.VISIBLE);
				tv1.setVisibility(View.INVISIBLE);
				tv2.setVisibility(View.INVISIBLE);
				tv3.setVisibility(View.INVISIBLE);
				tv5.setVisibility(View.INVISIBLE);
				break;
			case 5:
				tv5.setText(final_result);
				tv5.setVisibility(View.VISIBLE);
				tv1.setVisibility(View.INVISIBLE);
				tv2.setVisibility(View.INVISIBLE);
				tv3.setVisibility(View.INVISIBLE);
				tv4.setVisibility(View.INVISIBLE);
				break;
			default:
				super.handleMessage(msg);
				break;
			}
		};
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_UP){
			LogUtil.logMessage("wzb", "onTouchEvent ACTION_UP");
			ScreenSaveActivity.this.finish();
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTimer.cancel();
	}

}
