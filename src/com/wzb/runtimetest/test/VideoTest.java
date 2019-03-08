package com.wzb.runtimetest.test;

import java.util.Timer;
import java.util.TimerTask;

import com.wzb.runtimetest.BaseActivity;
import com.wzb.runtimetest.R;
import com.wzb.runtimetest.WApplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 8, 2019 10:16:55 AM	
 */
public class VideoTest extends BaseActivity{
	
	private Context mContext;
	
	MyVideoView video_view;
	private MediaController mediaController;
	private Timer mTimer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		mContext=VideoTest.this;
		LcdTest.setBrightness(VideoTest.this, 255);
		AudioManager mAudioManger=(AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mAudioManger.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
		video_view=(MyVideoView)findViewById(R.id.video_view);
		mediaController=new MediaController(mContext);
		video_view.setMediaController(mediaController);
		video_view.setVideoURI(Uri.parse("android.resource://com.wzb.runtimetest/"+R.raw.neffos_video));
		video_view.start();
		video_view.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.start();
				mp.setLooping(true);
			}
		});
		
		mTimer=new Timer();
		TimerTask task_finish=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(video_view!=null && video_view.isPlaying()){
					WApplication.sp_result.set(WApplication.SPRESULT_S[8], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[8], "pass");
				}else{
					WApplication.sp_result.set(WApplication.SPRESULT_S[8], "done");
					WApplication.sp_result.set(WApplication.SPRESULT_R[8], "fail");
				}
				WApplication.sp.set("runin", 10);
				VideoTest.this.finish();
			}
		};
		mTimer.schedule(task_finish, WApplication.sp_detail.get("video_c",2)*60*1000);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTimer.cancel();
	}
}
