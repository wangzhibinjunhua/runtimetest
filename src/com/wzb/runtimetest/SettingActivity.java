package com.wzb.runtimetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 26, 2019 7:55:01 PM	
 */
public class SettingActivity extends BaseActivity implements OnClickListener{
	
	
	private Context mContext;
	
	CheckBox cb_isCheckStation;
	CheckBox cb_testWithCharger;
	TextView tv_reboot;
	TextView tv_memory;
	TextView tv_emmc;
	TextView tv_receiver;
	TextView tv_battery;
	TextView tv_audio;
	TextView tv_vibrator;
	TextView tv_camera;
	TextView tv_video;
	TextView tv_lcd;
	TextView tv_gps;
	TextView tv_bt;
	TextView tv_wifi;
	TextView tv_light;
	TextView tv_proximity;
	TextView tv_gravity;
	TextView tv_fullbattery;
	TextView tv_backlight;
	TextView tv_quickcharge;
	TextView tv_screensave;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mContext=SettingActivity.this;
		initView();
	}
	
	private void initView(){
		cb_isCheckStation=(CheckBox)findViewById(R.id.cb_ischeckstation);
		cb_isCheckStation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				WApplication.sp.set("ischeckstation", isChecked);
			}
		});
		cb_isCheckStation.setChecked(WApplication.sp.get("ischeckstation", true));
		cb_testWithCharger=(CheckBox)findViewById(R.id.cb_testwithcharger);
		cb_testWithCharger.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				WApplication.sp.set("testwithcharger", isChecked);
			}
		});
		cb_testWithCharger.setChecked(WApplication.sp.get("testwithcharger", true));
		
		tv_reboot=(TextView)findViewById(R.id.setting_reboot);
		tv_reboot.setClickable(true);
		tv_reboot.setOnClickListener(this);
		
		tv_memory=(TextView)findViewById(R.id.setting_memory);
		tv_memory.setClickable(true);
		tv_memory.setOnClickListener(this);
		
		tv_emmc=(TextView)findViewById(R.id.setting_emmc);
		tv_emmc.setClickable(true);
		tv_emmc.setOnClickListener(this);
		
		tv_receiver=(TextView)findViewById(R.id.setting_receiver);
		tv_receiver.setClickable(true);
		tv_receiver.setOnClickListener(this);
		
		tv_battery=(TextView)findViewById(R.id.setting_battery);
		tv_battery.setClickable(true);
		tv_battery.setOnClickListener(this);
		
		
		tv_audio=(TextView)findViewById(R.id.setting_audio);
		tv_audio.setClickable(true);
		tv_audio.setOnClickListener(this);
		
		tv_vibrator=(TextView)findViewById(R.id.setting_vibrator);
		tv_vibrator.setClickable(true);
		tv_vibrator.setOnClickListener(this);
		
		tv_camera=(TextView)findViewById(R.id.setting_Camera);
		tv_camera.setClickable(true);
		tv_camera.setOnClickListener(this);
		
		tv_video=(TextView)findViewById(R.id.setting_video);
		tv_video.setClickable(true);
		tv_video.setOnClickListener(this);
		
		tv_lcd=(TextView)findViewById(R.id.setting_lcd);
		tv_lcd.setClickable(true);
		tv_lcd.setOnClickListener(this);
		
		tv_gps=(TextView)findViewById(R.id.setting_gps);
		tv_gps.setClickable(true);
		tv_gps.setOnClickListener(this);
		
		tv_bt=(TextView)findViewById(R.id.setting_bt);
		tv_bt.setClickable(true);
		tv_bt.setOnClickListener(this);
		
		tv_wifi=(TextView)findViewById(R.id.setting_wifi);
		tv_wifi.setClickable(true);
		tv_wifi.setOnClickListener(this);
		
		tv_light=(TextView)findViewById(R.id.setting_light);
		tv_light.setClickable(true);
		tv_light.setOnClickListener(this);
		
		tv_proximity=(TextView)findViewById(R.id.setting_proximity);
		tv_proximity.setClickable(true);
		tv_proximity.setOnClickListener(this);
		
		tv_gravity=(TextView)findViewById(R.id.setting_gravity);
		tv_gravity.setClickable(true);
		tv_gravity.setOnClickListener(this);
		
		tv_fullbattery=(TextView)findViewById(R.id.setting_fullbattery);
		tv_fullbattery.setClickable(true);
		tv_fullbattery.setOnClickListener(this);
		
		tv_backlight=(TextView)findViewById(R.id.setting_backlight);
		tv_backlight.setClickable(true);
		tv_backlight.setOnClickListener(this);
		
		tv_quickcharge=(TextView)findViewById(R.id.setting_quickcharge);
		tv_quickcharge.setClickable(true);
		tv_quickcharge.setOnClickListener(this);
		
		tv_screensave=(TextView)findViewById(R.id.setting_screensave);
		tv_screensave.setClickable(true);
		tv_screensave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int cur_setting_item_detail_id=-1;
		switch(v.getId()){
		
		case R.id.setting_reboot:
			cur_setting_item_detail_id=0;
			break;
		case R.id.setting_memory:
			cur_setting_item_detail_id=1;
			break;
		case R.id.setting_emmc:
			cur_setting_item_detail_id=2;
			break;
		case R.id.setting_receiver:
			cur_setting_item_detail_id=3;
			break;
		case R.id.setting_battery:
			cur_setting_item_detail_id=4;
			break;
		case R.id.setting_audio:
			cur_setting_item_detail_id=5;
			break;
		case R.id.setting_vibrator:
			cur_setting_item_detail_id=6;
			break;
		case R.id.setting_Camera:
			cur_setting_item_detail_id=7;
			break;
		case R.id.setting_video:
			cur_setting_item_detail_id=8;
			break;
		case R.id.setting_lcd:
			cur_setting_item_detail_id=9;
			break;
		case R.id.setting_gps:
			cur_setting_item_detail_id=10;
			break;
		case R.id.setting_bt:
			cur_setting_item_detail_id=11;
			break;
		case R.id.setting_wifi:
			cur_setting_item_detail_id=12;
			break;
		case R.id.setting_light:
			cur_setting_item_detail_id=13;
			break;
		case R.id.setting_proximity:
			cur_setting_item_detail_id=14;
			break;
		case R.id.setting_gravity:
			cur_setting_item_detail_id=15;
			break;
		case R.id.setting_fullbattery:
			cur_setting_item_detail_id=16;
			break;
		case R.id.setting_backlight:
			cur_setting_item_detail_id=17;
			break;
		case R.id.setting_quickcharge:
			cur_setting_item_detail_id=18;
			break;
		case R.id.setting_screensave:
			cur_setting_item_detail_id=19;
			break;
		default:
			break;
		}
		WApplication.sp.set("cur_setting_item_detail_id", cur_setting_item_detail_id);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(SettingActivity.this, SettingItemDetail.class);
		startActivity(intent);
	}

}
