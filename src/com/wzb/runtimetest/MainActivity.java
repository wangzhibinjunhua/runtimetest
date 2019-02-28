package com.wzb.runtimetest;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 25, 2019 2:20:52 PM	
 */
public class MainActivity extends BaseActivity{
	
	private Context mContext;
	Button btn_start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mContext=MainActivity.this;
		initView();
	}
	
	private void initView(){
		btn_start=(Button)findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(MainActivity.this, TestResultActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater=getMenuInflater();
	    inflater.inflate(R.menu.menu_setting,menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		switch(item.getItemId()){
		case R.id.option_testitem:
			intent.setClass(MainActivity.this, TestItemActivity.class);
			startActivity(intent);
			break;
		case R.id.option_settings:
			intent.setClass(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			break;
	
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
