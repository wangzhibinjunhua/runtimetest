package com.wzb.runtimetest;


import java.io.File;
import java.text.DecimalFormat;

import com.wzb.runtimetest.util.LogUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 25, 2019 2:20:52 PM	
 */
public class MainActivity extends Activity{
	
	private Context mContext;
	Button btn_start;
	
	TextView tv_sw_version,tv_ram_size,tv_rom_size,tv_flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.activity_main);
		mContext=MainActivity.this;
		WApplication.activityList.add(this);
		initView();
	}
	
	
	private void clearResult(){
		for(int i=0;i<19;i++){
			WApplication.sp_result.remove(WApplication.SPRESULT_S[i]);
			WApplication.sp_result.remove(WApplication.SPRESULT_R[i]);
		}
	}
	
	private void initView(){
		btn_start=(Button)findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start_test();
			}
		});
		
		tv_sw_version=(TextView)findViewById(R.id.tv_sw_version);
		tv_ram_size=(TextView)findViewById(R.id.tv_ram_size);
		tv_rom_size=(TextView)findViewById(R.id.tv_rom_size);
		tv_flag=(TextView)findViewById(R.id.tv_flag_result);
		
		tv_sw_version.setText(android.os.Build.DISPLAY);
		showRAMSize();
		showROMInfo();
		showResult();
	}
	
	private void showResult(){
		int id=Nvram.resultRead();
		LogUtil.logMessage("wzb", "showResult id="+id);
		if(id==3){
			tv_flag.setText("PASS");
		}else if(id==2){
			tv_flag.setText("FAIL");
		}else{
			tv_flag.setText("None");
		}
	}
	
	private void start_test(){
		int battery_level=getBatteryLevel();
		if(battery_level<50){
			Toast.makeText(this, "Please charge more than 50%", Toast.LENGTH_LONG).show();
			return;
		}
		clearResult();
		if(needTestReboot()){
			WApplication.sp.set("runin", -1);
			WApplication.sp.set("cur_reboot_count", WApplication.sp_detail.get("reboot_c", 60));
			PowerManager pManager=(PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
			pManager.reboot("reboot");
		}else{
			gotoResultActivity();
		}
		
	}
	
	@SuppressLint("NewApi")
	private int getBatteryLevel(){
		int level=0;
		BatteryManager manager = (BatteryManager) getSystemService(BATTERY_SERVICE);
		level=manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
		LogUtil.logMessage("wzb","getBatteryLevel level="+level);
		return level;
	}
	
	private Boolean needTestReboot(){
		return WApplication.sp.get("reboot_s", true);
		//return false;
	}
	
	private void gotoResultActivity(){
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(MainActivity.this, TestResultActivity2.class);
		startActivity(intent);
	}
	
	
	private void showRAMSize(){  
        ActivityManager am=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);  
        MemoryInfo mi=new MemoryInfo();  
        am.getMemoryInfo(mi);  
       // String[] available=fileSize(mi.availMem);  
        long total=mi.totalMem;  
		String strTotal="";
		if(total>1000){
			total/=1000;
			strTotal=total+" KB";
			if(total>1000){
				total/=1000;
				strTotal=total+" MB";
			}
		}
        tv_ram_size.setText(strTotal);     
    }  
    
    private void showROMInfo(){  
        File file=Environment.getDataDirectory();   
        StatFs statFs=new StatFs(file.getPath());    
        long blockSize=statFs.getBlockSize();    
        long totalBlocks=statFs.getBlockCount();    
            
        String[] total=fileSize(totalBlocks*blockSize);    
         int size=Integer.parseInt(total[0]);
         if(size>128){
        	 size=256;
         }else if(size>64){
        	 size=128;
         }else if(size>32){
        	 size=64;
         }else if(size>16){
        	 size=32;
         }else if(size>8){
        	 size=16;
         }else if(size>4){
        	 size =8;
         }else if(size>2){
        	 size=4;
         }
        tv_rom_size.setText(size+total[1]);     
    }  
    
    /*返回为字符串数组[0]为大小[1]为单位KB或者MB*/    
    private String[] fileSize(long size){    
        String str="";    
        if(size>=1000){    
            str="KB";    
            size/=1000;    
            if(size>=1000){    
                str="MB";    
                size/=1000;
                if(size>=1000){
                	str="GB";
                	size/=1000;

                }
            }    
        }    
        /*将每3个数字用,分隔如:1,000*/    
        DecimalFormat formatter=new DecimalFormat();    
        formatter.setGroupingSize(3);    
        String result[]=new String[2];    
        result[0]=formatter.format(size);    
        result[1]=str;    
        return result;    
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
		case R.id.option_report:
			intent.setClass(MainActivity.this, TestReport.class);
			startActivity(intent);
			break;
	
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
