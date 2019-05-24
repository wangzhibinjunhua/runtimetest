package com.wzb.runtimetest;

import com.wzb.runtimetest.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.text.TextUtils;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 28, 2019 10:44:57 AM	
 */
public class CommonReceiver extends BroadcastReceiver{
	
	private final Uri runtimetest = Uri.parse("android_secret_code://12345");

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final String action = intent.getAction();
		LogUtil.logMessage("wzb", "CommonReceiver:" + action);
		if(action.equals("android.intent.action.PRE_BOOT_COMPLETED")){

			//del by wzb 20190410 move to cmdline to init
			//set ccflag property
			/*
			if("1".equals(android.os.SystemProperties.get("ro.custom.set_detailmodel_tp",""))){
				if(Nvram.setDetailModel()==0){
					LogUtil.logMessage("wzb", "CommonReceiver set ccflag property ok");
				}else{
					LogUtil.logMessage("wzb", "CommonReceiver set ccflag property err");
				}
			}*/
			
		}
		else if (action.equals("android.provider.Telephony.SECRET_CODE")) {
			Uri uri = intent.getData();
			if(uri.equals(runtimetest)){
				Intent mintent = new Intent();
				mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mintent.setClass(context, MainActivity.class);
				context.startActivity(mintent);
			}
		} else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
			if (WApplication.sp.get("runin", 0) == -1) {
				int cur_reboot_count = WApplication.sp.get("cur_reboot_count", 0);
				if (cur_reboot_count > 1) {
					WApplication.sp.set("cur_reboot_count", cur_reboot_count - 1);
					PowerManager pManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
					pManager.reboot("reboot");
				} else {
					WApplication.sp_result.set(WApplication.SPRESULT_S[0], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[0], "pass");
					gotoResultActivity(context);
				}
			}
		}
	}

	private void gotoResultActivity(Context context) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, TestResultActivity2.class);
		context.startActivity(intent);
	}

}
