package com.wzb.runtimetest;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

import com.wzb.runtimetest.util.LogUtil;
import com.wzb.runtimetest.util.SharedPreferencesUtil;


/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date May 8, 2017 2:54:00 PM
 */
public class WApplication extends Application {

	/**
	 * 全局上下文环境.
	 */
	public static Context CONTEXT;
	/**
	 * SP读写工具.
	 */
	public static SharedPreferencesUtil sp;
	public static SharedPreferencesUtil sp_detail;
	public static SharedPreferencesUtil sp_result;
	/**
	 * 用户信息.
	 */
	// public static UserBean user;
	/**
	 * 文件根目录
	 */
	public static final String BASE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Health/";
	/**
	 * 图片文件目录
	 */
	public static final String IMAGE_FILE_PATH = BASE_FILE_PATH + "image/";

	/**
	 * 保存全部activity,便于管理
	 */
	public static List<Activity> activityList = new ArrayList<Activity>();

	public static boolean isNetWork = true;

	/**
	 * SP文件名.
	 */
	private final String SP_NAME = "runtimetest";

	public final static String[] ITEMNAME={"Reboot","Memory","EMMC","Receiver"
			,"Battery","Audio","Vibrator","Camera","Video","LCD","GPS"
			,"BT","WIFI","Light","Proximity","Gravity","Full Battery","ScreenSave"};
	
	public final static String[] SPITEM={"reboot_s","memory_s","emmc_s","receiver_s"
			,"battery_s","audio_s","vibrator_s","camera_s","video_s","lcd_s","gps_s"
			,"bt_s","wifi_s","light_s","proximity_s","gravity_s","full_battery_s","screensave_s"};
	
	
	public final static String[] SPRESULT_S={"reboot_s","memory_s","emmc_s","receiver_s"
			,"battery_s","audio_s","vibrator_s","camera_s","video_s","lcd_s","gps_s"
			,"bt_s","wifi_s","light_s","proximity_s","gravity_s","full_battery_s","screensave_s"};
	
	public final static String[] SPRESULT_R={"reboot_r","memory_r","emmc_r","receiver_r"
			,"battery_r","audio_r","vibrator_r","camera_r","video_r","lcd_r","gps_r"
			,"bt_r","wifi_r","light_r","proximity_r","gravity_r","full_battery_r","screensave_r"};
	@Override
	public void onCreate() {
		super.onCreate();

		CONTEXT = getApplicationContext();
		sp = new SharedPreferencesUtil(SP_NAME, SharedPreferencesUtil.PRIVATE, CONTEXT);
		sp_detail = new SharedPreferencesUtil("sp_detail", SharedPreferencesUtil.PRIVATE, CONTEXT);
		sp_result = new SharedPreferencesUtil("sp_result", SharedPreferencesUtil.PRIVATE, CONTEXT);
		LogUtil.openLog(); // 正式发布请注释此程序语句.

	}
	


}
