package com.wzb.runtimetest.test;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;
import com.wzb.runtimetest.util.LogUtil;

import android.content.Context;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 6, 2019 4:36:37 PM	
 */
public class GpsTest extends BaseActivity{
	

	private final ContentObserver mGpsMonitor = new ContentObserver(null) {
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			boolean enabled = mLocationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			LogUtil.logMessage("wzb", "gps enabled? " + enabled);
			String msg="GPS";
			if(enabled){
				msg="GPS: on";
				flag++;
			}else {
				msg="GPS: off";
				flag++;
			}
			Message gpsMsg=mHandler.obtainMessage();
			gpsMsg.what=1;
			gpsMsg.obj=msg;
			mHandler.sendMessage(gpsMsg);
		}
	};

	private LocationManager mLocationManager;
	private Context mContext;
	TextView tv_gps;
	private int flag=0;
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				String gpsStatus=(String)msg.obj;
				if(gpsStatus!=null){
					tv_gps.setText(gpsStatus);
				}
				break;
			case 2:
				openGPS(true);
				break;
			case 3:
				openGPS(false);
				break;
			case 4:
				openGPS(true);
				break;
			case 5:
				openGPS(false);
				break;
			case 6:
				openGPS(true);
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(flag>=2){
							WApplication.sp_result.set(WApplication.SPRESULT_S[10], "done");
							WApplication.sp_result.set(WApplication.SPRESULT_R[10], "pass");
						}else{
							WApplication.sp_result.set(WApplication.SPRESULT_S[10], "done");
							WApplication.sp_result.set(WApplication.SPRESULT_R[10], "fail");
						}
						WApplication.sp.set("runin", 3);
						GpsTest.this.finish();
					}
				}, 2000);
				break;
				default:
					break;
			}
		};
	};
	
	private void openGPS(boolean open) { 
		try{
	    if (Build.VERSION.SDK_INT <19) { 
	        Secure.setLocationProviderEnabled(mContext.getContentResolver(), 
	        LocationManager.GPS_PROVIDER, open); 
	    }else{ 
	        if(!open){ 
	            Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_OFF); 
	        }else{ 
	            Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_SENSORS_ONLY); 
	        } 
	    } 
		}catch (Exception e) {
			// TODO: handle exception
			LogUtil.logMessage("wzb", "open gps exception");
		}
	} 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		mContext=GpsTest.this;
		tv_gps=(TextView)findViewById(R.id.tv_gps);
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getContentResolver()
		.registerContentObserver(
				Settings.Secure
						.getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
				false, mGpsMonitor);
		Message gpsOn1=new Message();
		gpsOn1.what=2;
		mHandler.sendMessageDelayed(gpsOn1, 1000);
		Message gpsOff1=new Message();
		gpsOff1.what=3;
		mHandler.sendMessageDelayed(gpsOff1, 6000);
		
		Message gpsOn2=new Message();
		gpsOn2.what=4;
		mHandler.sendMessageDelayed(gpsOn2, 11000);
		
		Message gpsOff2=new Message();
		gpsOff2.what=5;
		mHandler.sendMessageDelayed(gpsOff2, 16000);
		
		Message gpsOn3=new Message();
		gpsOn3.what=6;
		mHandler.sendMessageDelayed(gpsOn3, 21000);
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getContentResolver().unregisterContentObserver(mGpsMonitor);
	}
	


}
