package com.wzb.runtimetest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 27, 2019 3:26:50 PM	
 */
public class SettingItemDetail extends BaseActivity implements OnClickListener{
	
	private Context mContext;
	
	TextView tv_setting_item_detail;
	CheckBox cb_setting_item_detail;
	
	TextView tv_setting_item_detail_count;
	TextView tv_setting_item_detail_count_value;
	

	TextView tv_setting_item_detail_time;
	TextView tv_setting_item_detail_time_value;
	

	TextView tv_setting_item_detail_other;
	TextView tv_setting_item_detail_other_value;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingitemdetail);
		mContext=SettingItemDetail.this;
		
		initView();
	}
	
	private void updateSp(int id,Boolean v){
		if(id>=0)WApplication.sp.set(WApplication.SPITEM[id], v);
	}
	
	private void initView(){
		tv_setting_item_detail=(TextView)findViewById(R.id.setting_item_detail);
		cb_setting_item_detail=(CheckBox)findViewById(R.id.cb_setting_item_detail);
		cb_setting_item_detail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				updateSp(WApplication.sp.get("cur_setting_item_detail_id", -1),isChecked);
			}
		});
		
		tv_setting_item_detail_count=(TextView)findViewById(R.id.setting_item_detail_count);
		tv_setting_item_detail_count.setClickable(true);
		tv_setting_item_detail_count.setOnClickListener(this);
		tv_setting_item_detail_count_value=(TextView)findViewById(R.id.setting_item_detail_count_value);
		
		tv_setting_item_detail_time=(TextView)findViewById(R.id.setting_item_detail_time);
		tv_setting_item_detail_time.setClickable(true);
		tv_setting_item_detail_time.setOnClickListener(this);
		tv_setting_item_detail_time_value=(TextView)findViewById(R.id.setting_item_detail_time_value);
		
		tv_setting_item_detail_other=(TextView)findViewById(R.id.setting_item_detail_other);
		tv_setting_item_detail_other.setClickable(true);
		tv_setting_item_detail_other.setOnClickListener(this);
		tv_setting_item_detail_other_value=(TextView)findViewById(R.id.setting_item_detail_other_value);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateView();
	}
	
	private void updateView(){
		int id=WApplication.sp.get("cur_setting_item_detail_id", -1);
		if(id>=0 && id<=17){
		tv_setting_item_detail.setText(WApplication.ITEMNAME[id]);
		cb_setting_item_detail.setChecked(WApplication.sp.get(WApplication.SPITEM[id], true));
		}
		switch(id){
		case 0:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("reboot_c", 60));
			break;
		case 1:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("memory_c", 156));
			break;
		case 2:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("emmc_c", 24));
			break;
		case 3:
			tv_setting_item_detail_time.setVisibility(View.VISIBLE);
			tv_setting_item_detail_time_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_time_value.setText(""+WApplication.sp_detail.get("receiver_t", 1800));
			break;
		case 4:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("battery_c", 1));
			
			tv_setting_item_detail_other.setVisibility(View.VISIBLE);
			tv_setting_item_detail_other.setText("Flashlight Brightness");
			tv_setting_item_detail_other_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_other_value.setText(""+WApplication.sp_detail.get("flashlight_brightness", 1));
			break;
		case 5:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("audio_c", 16));
			break;
		case 6:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("vibrator_c", 1));
			tv_setting_item_detail_time.setVisibility(View.VISIBLE);
			tv_setting_item_detail_time_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_time_value.setText(""+WApplication.sp_detail.get("vibrator_t", 2400));
			break;
		case 7:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("camera_c", 80));
			break;
		case 8:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("video_c", 34));
			break;
		case 9:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("lcd_c", 1300));
			break;
		case 10:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("gps_c", 1));
			break;
		case 11:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("bt_c", 1));
			break;
		case 12:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("wifi_c", 1));
			break;
		case 13:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("light_c", 1));
			break;
		case 14:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("proximity_c", 1));
			break;
		case 15:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("gravity_c", 1));
			break;
		case 16:
			tv_setting_item_detail_count.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_count_value.setText(""+WApplication.sp_detail.get("fullbattery_c", 1));
			
			tv_setting_item_detail_other.setVisibility(View.VISIBLE);
			tv_setting_item_detail_other.setText("Target Power");
			tv_setting_item_detail_other_value.setVisibility(View.VISIBLE);
			tv_setting_item_detail_other_value.setText(""+WApplication.sp_detail.get("target_power", 100));
			break;
		case 17:
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
