package com.wzb.runtimetest.test;

import java.util.Timer;
import java.util.TimerTask;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;
import com.wzb.runtimetest.util.LogUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 7, 2019 1:43:41 PM
 */
public class LcdTest extends BaseActivity {

	TextView tv_lcd;
	Timer mTimer;
	int test_count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lcd);
		tv_lcd = (TextView) findViewById(R.id.tv_lcd);
		setBrightness(LcdTest.this, 255);
		mTimer = new Timer();
		TimerTask task_red = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		TimerTask task_green = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(2);
			}
		};
		TimerTask task_blue = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(3);
			}
		};
		TimerTask task_black = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(4);
			}
		};

		TimerTask task_white = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(5);
			}
		};
		mTimer.schedule(task_red, 0,3000);
		mTimer.schedule(task_green, 600,3000);
		mTimer.schedule(task_blue, 1200,3000);
		mTimer.schedule(task_black, 1800,3000);
		mTimer.schedule(task_white, 2400,3000);
		
		//test time
		final long test_time=WApplication.sp_detail.get("lcd_c", 13)*3000;
		TimerTask task_finish = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LogUtil.logMessage("wzb", "lcd test_count="+test_count+" testtime="+test_time);
				if(test_count>(test_time/3000 -2)){
					WApplication.sp_result.set(WApplication.SPRESULT_S[9], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[9], "pass");
				}else{
					WApplication.sp_result.set(WApplication.SPRESULT_S[9], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[9], "fail");
				}
				WApplication.sp.set("runin", 6);
				LcdTest.this.finish();
			}
		};
		mTimer.schedule(task_finish, test_time);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				tv_lcd.setBackgroundColor(Color.RED);
				test_count++;
				break;
			case 2:
				tv_lcd.setBackgroundColor(Color.GREEN);
				break;
			case 3:
				tv_lcd.setBackgroundColor(Color.BLUE);
				break;
			case 4:
				tv_lcd.setBackgroundColor(Color.BLACK);
				break;
			case 5:
				tv_lcd.setBackgroundColor(Color.WHITE);
				break;
			default:
				break;
			}
		};
	};

	private void setBrightness(Activity activity, int brightness) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
		activity.getWindow().setAttributes(lp);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTimer.cancel();
	}

}
