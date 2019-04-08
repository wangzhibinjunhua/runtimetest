package com.wzb.runtimetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.wzb.runtimetest.util.LogUtil;

import android.app.IntentService;
import android.content.Intent;
import android.os.Vibrator;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Apr 8, 2019 2:27:55 PM	
 */
public class CoreIntentService extends IntentService{
	private Vibrator mVibrator;
	
	public CoreIntentService(){
		super("coreintentservice");
	}

	public CoreIntentService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		int key = intent.getIntExtra("key", 0);
		switch(key){
		case 2:
			test_memory();
			break;
		case 3:
			test_emmc();
			break;
		default:
			break;
		}
	}
	

	private void test_memory() {
		if (!WApplication.sp.get("memory_s", true)) {
			return;
		}
		Intent intent = new Intent();
		intent.setAction("custom.android.memory");
		intent.putExtra("status", "testing");
		sendBroadcast(intent);
		Boolean memory_flag = true;
		int test_memory_count = WApplication.sp_detail.get("memory_c", 156);
		for (int j = 0; j < test_memory_count*100; j++) {
			byte buffer1[] = new byte[1024 * 128];
			InputStream inputStream1 = getResources().openRawResource(R.raw.test_memory);
			try {
				int len1 = inputStream1.read(buffer1);
				//LogUtil.logMessage("wzb", "len1=" + len1);
				while (len1 > 0) {
					len1 = inputStream1.read(buffer1);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputStream1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			byte buffer2[] = new byte[1024 * 128];
			InputStream inputStream2 = getResources().openRawResource(R.raw.test_memory);
			try {
				int len2 = inputStream2.read(buffer2);
				//LogUtil.logMessage("wzb", "len2=" + len2);
				while (len2 > 0) {
					len2 = inputStream2.read(buffer2);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputStream2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (int i = 0; i < buffer1.length; i++) {
				if (buffer1[i] != buffer2[i]) {
					LogUtil.logMessage("wzb", "memory err!");
					memory_flag = false;
					break;
				}
			}
			if (!memory_flag) {
				break;
			}
		}

		intent.setAction("custom.android.memory");
		intent.putExtra("status", "done");
		String result = "";
		if (memory_flag) {
			result = "pass";
		} else {
			result = "fail";
		}
		intent.putExtra("result", result);
		sendBroadcast(intent);
		WApplication.sp_result.set(WApplication.SPRESULT_S[1], "done");
		WApplication.sp_result.set(WApplication.SPRESULT_R[1], result);

	}

	private void test_emmc() {

		if (!WApplication.sp.get("emmc_s", true)) {
			return;
		}
		Intent intent = new Intent();
		intent.setAction("custom.android.emmc");
		intent.putExtra("status", "testing");
		sendBroadcast(intent);
		Boolean emmc_flag = false;
		FileOutputStream fileOutputStream = null;
		File temp_file = new File(WApplication.BASE_FILE_PATH + "emmc_i.mp3");
		InputStream inputStream = null;
		int test_emmc_count = WApplication.sp_detail.get("emmc_c", 24);
		for (int j = 0; j < test_emmc_count; j++) {
			try {
				File files = new File(WApplication.BASE_FILE_PATH);
				if (!files.exists()) {
					files.mkdir();
				}
				inputStream = getResources().openRawResource(R.raw.test_emmc);
				fileOutputStream = new FileOutputStream(temp_file, true);
				byte[] buffer = new byte[1024];
				int n = 0;
				long temp_size = 0;
				while ((n = inputStream.read(buffer)) != -1) {
					temp_size += n;
					fileOutputStream.write(buffer, 0, n);
					if (temp_size == 1024 * 1000) {
						break;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					fileOutputStream.close();
					if (temp_file != null) {
						if (temp_file.exists() && temp_file.isFile()) {
							//
							long temp_file_size = temp_file.length();
							//LogUtil.logMessage("wzb", "size=" + temp_file_size);
							if (temp_file_size == 1024*1000) {
								emmc_flag = true;
							}
							temp_file.delete();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			if(!emmc_flag){
				break;
			}
		}
		
		intent.setAction("custom.android.emmc");
		intent.putExtra("status", "done");
		String result = "";
		if (emmc_flag) {
			result = "pass";
		} else {
			result = "fail";
		}
		intent.putExtra("result", result);
		sendBroadcast(intent);
		WApplication.sp_result.set(WApplication.SPRESULT_S[2], "done");
		WApplication.sp_result.set(WApplication.SPRESULT_R[2], result);

	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
