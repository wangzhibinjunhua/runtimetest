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
	TextView tv_reboot;

	TextView tv_reboot_status;

	TextView tv_reboot_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		mContext = TestResultActivity.this;
		initView();

	}

	private void initView() {
		tv_reboot = (TextView) (findViewById(R.id.result_reboot));
		tv_reboot_status = (TextView) findViewById(R.id.result_reboot_status);
		tv_reboot_result = (TextView) findViewById(R.id.result_reboot_result);

	}

}
