package com.wzb.runtimetest.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;
import com.wzb.runtimetest.util.LogUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 7, 2019 5:00:00 PM
 */
public class CameraTest extends BaseActivity implements Callback {

	private Context mContext;
	SurfaceView surfaceView;
	SurfaceHolder holder;
	int cameraCount = 0;
	Camera mCamera = null;
	private String filepath = "";// 照片保存路径
	private int cameraPosition = 1;// 0代表前置摄像头，1代表后置摄像头
	private SoundPool soundPool;
    private int soundId;
	Timer mTimer;
	static int shutter_count = 0;
	TimerTask task_shutter, task_toggle;
	TimerTask task_finish;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		mContext = CameraTest.this;

		surfaceView = (SurfaceView) findViewById(R.id.sv_camera);
		holder = surfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundId = soundPool.load(this, R.raw.camera_click, 1);
		cameraCount = Camera.getNumberOfCameras();
		LogUtil.logMessage("wzb", "onCreate cameraCount=" + cameraCount);
		mTimer = new Timer();
		task_shutter = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		task_toggle = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(2);
			}
		};
		task_finish = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LogUtil.logMessage("wzb", "shutter_count="+shutter_count);
				if(shutter_count>=WApplication.sp_detail.get("camera_c", 80)){
					WApplication.sp_result.set(WApplication.SPRESULT_S[7], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[7], "pass");
				}else{
					WApplication.sp_result.set(WApplication.SPRESULT_S[7], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[7], "fail");
				}
				WApplication.sp.set("runin", 9);
				
				CameraTest.this.finish();
			}
		};
		shutter_count=0;
		mTimer.schedule(task_shutter, 2000);
		long finish_time=WApplication.sp_detail.get("camera_c", 80)*7000+2000;
		mTimer.schedule(task_finish, finish_time);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				shutter();
				break;
			case 2:
				toggleCamera();

				break;
			case 9:
				if(shutter_count>=WApplication.sp_detail.get("camera_c", 80)){
					WApplication.sp_result.set(WApplication.SPRESULT_S[7], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[7], "pass");
				}else{
					WApplication.sp_result.set(WApplication.SPRESULT_S[7], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[7], "fail");
				}
				WApplication.sp.set("runin", 9);
				if(task_finish!=null){
					task_finish.cancel();
				}
				CameraTest.this.finish();
				LogUtil.logMessage("wzb", "handle msg 9 fail");
				break;
			default:
				super.handleMessage(msg);
				break;
			}
		};
	};

	
	private void toggleCamera() {
		LogUtil.logMessage("wzb", "shutter mCamera=" + mCamera);
		if (mCamera == null){
			mHandler.sendEmptyMessage(9);
			LogUtil.logMessage("wzb","toggleCamera null");
			return;
		}
		if (cameraPosition == 1) {
			// 现在是后置，变更为前置

			if(mCamera!=null){
				mCamera.stopPreview();
				mCamera.release();

				mCamera = null;
			}
			

			try {
				mCamera = Camera.open(1);
				mCamera.setPreviewDisplay(holder);
				mCamera.setDisplayOrientation(90);
				mCamera.startPreview();
				cameraPosition = 0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else {
			// 现在是前置， 变更为后置
			if(mCamera!=null){
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
			try {
				mCamera = Camera.open(0);
				mCamera.setPreviewDisplay(holder);
				mCamera.setDisplayOrientation(90);
				mCamera.startPreview();
				cameraPosition = 1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		mHandler.sendEmptyMessageDelayed(1, 1000);
	}
	
	private void shutter(){
		if(mCamera==null){
			mHandler.sendEmptyMessage(9);
			return;
		}

		Parameters params = mCamera.getParameters();
		params.setPictureFormat(PixelFormat.JPEG);
		mCamera.setParameters(params);

		mCamera.takePicture(shutterCallback, null, jpeg);
		

	}
        

	ShutterCallback shutterCallback=new ShutterCallback() {
		
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			LogUtil.logMessage("wzb","shutter");
			soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
		}
	};
	PictureCallback jpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				// 自定义文件保存路径 以拍摄时间区分命名
				File files = new File(WApplication.BASE_FILE_PATH);
				if (!files.exists()) {
					files.mkdir();
				}
				filepath = WApplication.BASE_FILE_PATH + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
						+ ".jpg";
				File file = new File(filepath);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
				camera.stopPreview();
				camera.startPreview();
				bitmap.recycle();
				shutter_count++;
				LogUtil.logMessage("wzb", "aaaa shutter_count=" + shutter_count);
				if(shutter_count>=WApplication.sp_detail.get("camera_c", 80)){
					mHandler.sendEmptyMessage(9);
				}else{
					mHandler.sendEmptyMessageDelayed(2, 1000);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mCamera == null) {
			mCamera = Camera.open(0);
			LogUtil.logMessage("wzb", "mCamera=" + mCamera);
			try {
				mCamera.setDisplayOrientation(90);
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
			holder = null;
			surfaceView = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTimer != null)
			mTimer.cancel();
	}
}
