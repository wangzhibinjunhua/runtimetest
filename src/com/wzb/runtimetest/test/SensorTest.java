package com.wzb.runtimetest.test;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;
import com.wzb.runtimetest.util.LogUtil;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
// yangkun add for Cycle  AgeTest  start 
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
// add end

public class SensorTest extends BaseActivity implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mGSensor;
	private Sensor mALSSensor;
	private Sensor mPSSensor;
	private Sensor mMagnetic; //wlf
	private Sensor mGySensor;
	private TextView GSensor;
	private TextView GSensor_X;
	private TextView GSensor_Y;
	private TextView GSensor_Z;
	private TextView GySensor;
	private TextView GySensor_X;
	private TextView GySensor_Y;
	private TextView GySensor_Z;
	private TextView ALSensorresult;
	private TextView ALSensor;
	private TextView ALSensorinfo;
	private TextView PSensorresult;
	private TextView PSensor;
	private TextView PSensorinfo;
	private TextView Magnetic;
	private int	count = 1;
	private TextView Magnetic_Value;
	// add end
	
	
	
	Boolean light_result=false;
	Boolean proximity_result=false;
	Boolean gravity_result=false;
	
	private Handler mHandler = new Handler() {  
		public void handleMessage(Message msg) {
			final int type = msg.what;
			// 
		}
	};
	private Runnable mSensorTest = new Runnable() {
		@Override
		public void run() {	
			if (WApplication.sp.get("light_s",true )) {
				String status="done";
				String result="";
				if(light_result){
					result="pass";
				}else{
					result="fail";
				}
				WApplication.sp_result.set(WApplication.SPRESULT_S[13], status);
				WApplication.sp_result.set(WApplication.SPRESULT_R[13], result);
			}
			
			if (WApplication.sp.get("proximity_s",true )) {
				String status="done";
				String result="";
				if(proximity_result){
					result="pass";
				}else{
					result="fail";
				}
				WApplication.sp_result.set(WApplication.SPRESULT_S[14], status);
				WApplication.sp_result.set(WApplication.SPRESULT_R[14], result);
			}
			
			if (WApplication.sp.get("gravity_s",true )) {
				String status="done";
				String result="";
				if(gravity_result){
					result="pass";
				}else{
					result="fail";
				}
				WApplication.sp_result.set(WApplication.SPRESULT_S[15], status);
				WApplication.sp_result.set(WApplication.SPRESULT_R[15], result);
			}
			
		    SensorTest.this.finish();
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_test);
		
		mSensorManagerInit();
		GsensorInit();Gsensor();
		GysensorInit();Gysensor();
		MagneticInit();Magnetic();
		ALSensorInit();ALSensor();
		PSensorInit();PSensor();
		mHandler.postDelayed(mSensorTest, 5000);
	}

	public void PSensorInit() {
		// TODO Auto-generated method stub
		PSensor = (TextView) findViewById(R.id.Psensor);
		PSensorinfo = (TextView) findViewById(R.id.Pinfo);
		PSensorresult = (TextView) findViewById(R.id.Pruselt);	
	}

	public void PSensor() {
		// TODO Auto-generated method stub
		if (null != mPSSensor ) {
			PSensor.setText("PSensor:");
			mSensorManager.registerListener((SensorEventListener) this, mPSSensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			PSensor.setText("PSensor not support");
		}
	}

	public void ALSensorInit() {
		// TODO Auto-generated method stub
		ALSensor = (TextView) findViewById(R.id.ALsensor);
		ALSensorinfo = (TextView) findViewById(R.id.ALinfo);
		ALSensorresult = (TextView) findViewById(R.id.ALruselt);	
	}

	public void ALSensor() {
		// TODO Auto-generated method stub
		if(null != mALSSensor ) {
			ALSensor.setText("ALSensor:");
			mSensorManager.registerListener((SensorEventListener) this, mALSSensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			ALSensor.setText("ALSensor not support");
		}
	}

	public void Gysensor() {
		// TODO Auto-generated method stub
		if (mGySensor != null) {
			GySensor.setText("Gysensor:");
			mSensorManager.registerListener((SensorEventListener) this, mGySensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			GySensor_X.setVisibility(View.GONE);
			GySensor_Y.setVisibility(View.GONE);
			GySensor_Z.setVisibility(View.GONE);
			GySensor.setText("Gysensor not support");
		}
	}
	public void Magnetic() {
		// TODO Auto-generated method stub
		if (mMagnetic != null) {
			Magnetic.setText(" MagneticSensor:");
			mSensorManager.registerListener((SensorEventListener) this, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
		}
		else{
			Magnetic_Value.setVisibility(View.GONE);
			Magnetic.setText("Magnetic not support");
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mSensorManager.unregisterListener((SensorEventListener) this);
		super.onDestroy();
	}


	public void	Gsensor() {
		if (mGSensor != null) {
			GSensor.setText("Gsensor:");
			mSensorManager.registerListener((SensorEventListener) this, mGSensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			GSensor_X.setVisibility(View.GONE);
			GSensor_Y.setVisibility(View.GONE);
			GSensor_Z.setVisibility(View.GONE);
			GSensor.setText("Gsensor not support");
		}
	}
	public void	mSensorManagerInit() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mGSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mALSSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		mPSSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mGySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
	}
	public void GysensorInit() {
		GySensor = (TextView) findViewById(R.id.Gysensor);
		GySensor_X = (TextView) findViewById(R.id.Gysensor_X);
		GySensor_Y = (TextView) findViewById(R.id.Gysensor_Y);
		GySensor_Z = (TextView) findViewById(R.id.Gysensor_Z);
	}
	public void GsensorInit() {
		GSensor = (TextView) findViewById(R.id.Gsensor);
		GSensor_X = (TextView) findViewById(R.id.Gsensor_X);
		GSensor_Y = (TextView) findViewById(R.id.Gsensor_Y);
		GSensor_Z = (TextView) findViewById(R.id.Gsensor_Z);
	}
	public void MagneticInit() {
		Magnetic = (TextView) findViewById(R.id.Magnetic);
		Magnetic_Value = (TextView) findViewById(R.id.Magnetic_X);
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//LogUtil.logMessage("wzb", "sensor type:"+event.sensor.getType());
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			
			GSensor_X.setText("X values is   "+event.values[0]+"");
			GSensor_Y.setText("Y values is   "+event.values[1]+"");
			GSensor_Z.setText("Z values is   "+event.values[2]+"");
			gravity_result=true;
		}
		if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			GySensor_X.setText("GySensor X values is   "+event.values[0]+"");
			GySensor_Y.setText("GySensor Y values is   "+event.values[1]+"");
			GySensor_Z.setText("GySensor Z values is   "+event.values[2]+"");
		}
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			if ( count++ == 20) { //磁场传感器很敏感，每20个变化，显示一次数值
	            double value = Math.sqrt(event.values[0]*event.values[0] + event.values[1]*event.values[1]
	                    +event.values[2]*event.values[2]);
	            String str = String.format("X:%8.4f , Y:%8.4f , Z:%8.4f \n总值为：%8.4f",
	                    event.values[0],event.values[1],event.values[2],value);             
	            count = 1;      
	            Magnetic_Value.setText("GySensor X values is   "+str);
			}
		}
		if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
			ALSensorresult.setText("als values is "+event.values[0]+"");
			light_result=true;
		}
		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
			LogUtil.logMessage("wzb", "TYPE_PROXIMITY :"+event.values[0]);
			PSensorresult.setText("ps values is "+event.values[0]+"");
			proximity_result=true;
		}
	}
}
