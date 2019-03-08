package com.wzb.runtimetest.test;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 7, 2019 2:55:10 PM
 */
public class AudioTest extends BaseActivity {
	
	private Context mContext;
	TextView tv_testtime;
	
	AudioManager mAudioManger;
	MediaPlayer mMediaPlayer;
	long test_second=0;
	long start_time=0;
	
	Timer mTimer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		mContext=AudioTest.this;
		start_time=System.currentTimeMillis();
		tv_testtime = (TextView) findViewById(R.id.tv_audio_testtime);
		mAudioManger=(AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mAudioManger.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
		mAudioManger.setMode(AudioManager.STREAM_MUSIC);
		mAudioManger.setSpeakerphoneOn(true);
		
		mMediaPlayer = MediaPlayer.create(mContext, R.raw.test_music);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.start();
		
		mTimer=new Timer();
		TimerTask task_time=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(1);
			}
		};
		mTimer.schedule(task_time, 0,1000);
		TimerTask task_finish=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(mMediaPlayer.isPlaying()){
					WApplication.sp_result.set(WApplication.SPRESULT_S[5], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[5], "pass");
				}else{
					WApplication.sp_result.set(WApplication.SPRESULT_S[5], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[5], "fail");
				}
				WApplication.sp.set("runin", 8);
				AudioTest.this.finish();
			}
		};
		mTimer.schedule(task_finish, WApplication.sp_detail.get("audio_c", 1)*4*60*1000);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				test_second=(System.currentTimeMillis()-start_time)/1000;
				tv_testtime.setText(ReceiverTest.formatSeconds(test_second));
				break;
			default:
				super.handleMessage(msg);
				break;
			}
		};
	};
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();		
			
		}
		if(mAudioManger != null){
			mAudioManger.setMode(AudioManager.MODE_NORMAL);
		}
		mTimer.cancel();
	}

	

}
