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
public class ReceiverTest extends BaseActivity {
	
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
		setContentView(R.layout.activity_receiver);
		mContext=ReceiverTest.this;
		start_time=System.currentTimeMillis();
		tv_testtime = (TextView) findViewById(R.id.tv_receiver_testtime);
		mAudioManger=(AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mAudioManger.setMode(AudioManager.MODE_IN_COMMUNICATION);
		mAudioManger.setSpeakerphoneOn(false);
		mAudioManger.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
		setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
		mAudioManger.setStreamVolume(AudioManager.STREAM_VOICE_CALL, mAudioManger.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), AudioManager.FLAG_SHOW_UI);
		mMediaPlayer = MediaPlayer.create(mContext, R.raw.test_audio);
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
					WApplication.sp_result.set(WApplication.SPRESULT_S[3], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[3], "pass");
				}else{
					WApplication.sp_result.set(WApplication.SPRESULT_S[3], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[3], "fail");
				}
				WApplication.sp.set("runin", 7);
				ReceiverTest.this.finish();
			}
		};
		mTimer.schedule(task_finish, WApplication.sp_detail.get("receiver_t", 180)*1000);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				test_second=(System.currentTimeMillis()-start_time)/1000;
				tv_testtime.setText(formatSeconds(test_second));
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

	public static String formatSeconds(long seconds) {
		String standardTime;
		if (seconds <= 0) {
			standardTime = "00:00";
		} else if (seconds < 60) {
			standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
		} else if (seconds < 3600) {
			standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
		} else {
			standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60,
					seconds % 60);
		}
		return standardTime;
	}

}
