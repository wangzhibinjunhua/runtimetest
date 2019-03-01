package com.wzb.runtimetest;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 28, 2019 11:18:24 AM
 */
public class TestResultActivity extends BaseActivity {

	private Context mContext;
	TextView tv_reboot, tv_memory, tv_emmc, tv_receiver, tv_battery, tv_audio,
	tv_vibrator,tv_camera,tv_video,tv_lcd,tv_gps,tv_bt,tv_wifi,tv_light,tv_proximity,
	tv_gravity,tv_fullbattery;
	
	TextView tv_reboot_status, tv_memory_status, tv_emmc_status, tv_receiver_status, tv_battery_status, tv_audio_status,
	tv_vibrator_status,tv_camera_status,tv_video_status,tv_lcd_status,tv_gps_status,tv_bt_status,tv_wifi_status,tv_light_status,tv_proximity_status,
	tv_gravity_status,tv_fullbattery_status;

	
	TextView tv_reboot_result, tv_memory_result, tv_emmc_result, tv_receiver_result, tv_battery_result, tv_audio_result,
	tv_vibrator_result,tv_camera_result,tv_video_result,tv_lcd_result,tv_gps_result,tv_bt_result,tv_wifi_result,tv_light_result,tv_proximity_result,
	tv_gravity_result,tv_fullbattery_result;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		mContext = TestResultActivity.this;
		initView();

	}

	private void initView() {
		tv_reboot=(TextView)(findViewById(R.id.result_reboot));
		tv_reboot_status=(TextView)findViewById(R.id.result_reboot_status);
		tv_reboot_result=(TextView)findViewById(R.id.result_reboot_result);
		
		tv_memory=(TextView)(findViewById(R.id.result_memory));
		tv_memory_status=(TextView)findViewById(R.id.result_memory_status);
		tv_memory_result=(TextView)findViewById(R.id.result_memory_result);
		
		tv_emmc=(TextView)(findViewById(R.id.result_emmc));
		tv_emmc_status=(TextView)findViewById(R.id.result_emmc_status);
		tv_emmc_result=(TextView)findViewById(R.id.result_emmc_result);
		
		tv_receiver=(TextView)(findViewById(R.id.result_receiver));
		tv_receiver_status=(TextView)findViewById(R.id.result_receiver_status);
		tv_receiver_result=(TextView)findViewById(R.id.result_receiver_result);
		
		
		tv_battery=(TextView)(findViewById(R.id.result_battery));
		tv_battery_status=(TextView)findViewById(R.id.result_battery_status);
		tv_battery_result=(TextView)findViewById(R.id.result_battery_result);
		tv_audio=(TextView)(findViewById(R.id.result_audio));
		tv_audio_status=(TextView)findViewById(R.id.result_audio_status);
		tv_audio_result=(TextView)findViewById(R.id.result_audio_result);
		tv_vibrator=(TextView)(findViewById(R.id.result_vibrator));
		tv_vibrator_status=(TextView)findViewById(R.id.result_vibrator_status);
		tv_vibrator_result=(TextView)findViewById(R.id.result_vibrator_result);
		
		tv_camera=(TextView)(findViewById(R.id.result_camera));
		tv_camera_status=(TextView)findViewById(R.id.result_camera_status);
		tv_camera_result=(TextView)findViewById(R.id.result_camera_result);

	}

}
