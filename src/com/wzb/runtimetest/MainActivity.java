package com.wzb.runtimetest;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 25, 2019 2:20:52 PM	
 */
public class MainActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			break;
		case R.id.option_debugger:
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
