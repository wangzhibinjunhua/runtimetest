package com.wzb.runtimetest;

import com.wzb.runtimetest.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 28, 2019 10:44:57 AM	
 */
public class CommonReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final String action=intent.getAction();
		LogUtil.logMessage("wzb", "CommonReceiver:"+action);
	}

}
