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
	Camera mCamera = null;
	private String filepath = "";// 照片保存路径
	private int cameraPosition = 1;// 0代表前置摄像头，1代表后置摄像头
	private SoundPool soundPool;
    private int soundId;
	Timer mTimer;
	int shutter_count=0;
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
        // 加载声音资源
        soundId = soundPool.load(this, R.raw.camera_click, 1);

		mTimer = new Timer();
		TimerTask task_back = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		TimerTask task_front = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(2);
			}
		};
		TimerTask task_finish = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(shutter_count>=WApplication.sp_detail.get("camera_c", 8)){
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
		
		mTimer.schedule(task_back, 1000);
		mTimer.schedule(task_front, 4000,3000);
		long finish_time=WApplication.sp_detail.get("camera_c", 80)*3000+4000;
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
				shutter();
				break;
			case 9:
				WApplication.sp_result.set(WApplication.SPRESULT_S[7], "done");
				WApplication.sp_result.set(WApplication.SPRESULT_R[7], "fail");
				WApplication.sp.set("runin", 9);
				CameraTest.this.finish();
				break;
			default:
				super.handleMessage(msg);
				break;
			}
		};
	};
	
	private void toggleCamera(){
		if(mCamera==null)return;
		//切换前后摄像头
        int cameraCount = 0;
        CameraInfo cameraInfo = new CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        LogUtil.logMessage("wzb", "cameracount="+cameraCount);
        for(int i = 0; i < cameraCount; i ++  ) {
        	
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if(cameraPosition == 1) {
                //现在是后置，变更为前置
                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                   
                    try {
                    	 mCamera = Camera.open(i);//打开当前选中的摄像头
                    	mCamera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    	  mCamera.setDisplayOrientation(90);
                          mCamera.startPreview();//开始预览
                          cameraPosition = 0;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }catch (Exception e) {
						// TODO: handle exception
					}
                  
                    break;
                }
                } else {
                    //现在是前置， 变更为后置
                    if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
                    	mCamera.stopPreview();//停掉原来摄像头的预览
                    	mCamera.release();//释放资源
                    	mCamera = null;//取消原来摄像头
                    	
                        try {
                        	mCamera = Camera.open(i);//打开当前选中的摄像头
                        	mCamera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                        	 mCamera.setDisplayOrientation(90);
                             mCamera.startPreview();//开始预览
                             cameraPosition = 1;
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }catch (Exception e) {
							// TODO: handle exception
						}
                       
                        break;
                    }
                }

            }
	}
	
	private void shutter(){
		if(mCamera==null){
			mHandler.sendEmptyMessage(9);
			return;
		}
		soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
		mCamera.autoFocus(new AutoFocusCallback() {//自动对焦
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                //if(success) {
                    //设置参数，并拍照
                    Parameters params = camera.getParameters();
                    params.setPictureFormat(PixelFormat.JPEG);//图片格式
                    //params.setPreviewSize(800, 480);//图片大小
                    camera.setParameters(params);//将参数设置到我的camera
                    camera.takePicture(null, null, jpeg);//将拍摄到的照片给自定义的对象
                //}
            }
        });
	}

	// 创建jpeg图片回调数据对象
	PictureCallback jpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
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
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩的流里面
				bos.flush();// 刷新此缓冲区的输出流
				bos.close();// 关闭此输出流并释放与此流有关的所有系统资源
				camera.stopPreview();// 关闭预览 处理数据
				camera.startPreview();// 数据处理完后继续开始预览
				bitmap.recycle();// 回收bitmap空间
				shutter_count++;
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
		// TODO Auto-generated method stub
		if (mCamera == null) {
			mCamera = Camera.open();
			LogUtil.logMessage("wzb", "mCamera="+mCamera);
			try {
				mCamera.setDisplayOrientation(90);
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		super.onDestroy();
		mTimer.cancel();
	}
}
