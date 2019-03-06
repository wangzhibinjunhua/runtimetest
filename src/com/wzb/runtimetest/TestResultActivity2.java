package com.wzb.runtimetest;

import java.util.ArrayList;
import java.util.List;

import com.wzb.runtimetest.test.GpsTest;
import com.wzb.runtimetest.test.SensorTest;
import com.wzb.runtimetest.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 28, 2019 11:18:24 AM
 */
public class TestResultActivity2 extends BaseActivity implements OnScrollListener {

	private Context mContext;

	private ResultAdapter resultAdapter;

	private ListView resultListView = null;
	private ResultReceiver mResultReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result2);
		mContext = TestResultActivity2.this;
		init();
		initView();
		

	}
	//WApplication.sp.set("runin", 1);
	//runin 1,开始测试,service后台测试 vibrator emmc memory battery
	//2:测试完sensor
	//3.gps
	//4.bt
	//5.wifi
	
	private void init(){
		WApplication.sp.set("runin", 1);
		start_coreService();
		
		
	}
	
	private void start_coreService(){
		Intent intent=new Intent(mContext,CoreService.class);
		startService(intent);
		
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};

	private void initView() {
		resultListView = (ListView) findViewById(R.id.lv_resultitem);
		initAdapter();
		resultListView.setAdapter(resultAdapter);
		resultListView.setOnScrollListener(this);

	}
	
	private String getStatus(int id){
		return WApplication.sp_result.get(WApplication.SPRESULT_S[id], "None");
	}
	
	private String getResult(int id){
		return WApplication.sp_result.get(WApplication.SPRESULT_R[id], "None");
	}

	private void initAdapter() {
		List<ResultItemBean> resultItems = new ArrayList<ResultItemBean>();
		for(int i=0;i<17;i++){
			ResultItemBean items=new ResultItemBean(WApplication.ITEMNAME[i],getStatus(i),getResult(i));
			resultItems.add(items);
		}
		
		resultAdapter=new ResultAdapter(resultItems);
	}
	
	
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.logMessage("wzb", "resultactivity onResume");
		registerBr();
		updateData();
		nextTest();
	}
	
	private void nextTest(){
		int nexttest=WApplication.sp.get("runin",0);
		if(nexttest==1){//test sensor
			mHandler.postDelayed(new  Runnable() {
				public void run() {
					Intent intent = new Intent();
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setClass(mContext, SensorTest.class);
					startActivity(intent);
				}
			}, 5000);
		}else if(nexttest==2){//test gps
			if(WApplication.sp.get("gps_s", true)){
				mHandler.postDelayed(new  Runnable() {
					public void run() {
						Intent intent = new Intent();
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setClass(mContext, GpsTest.class);
						startActivity(intent);
					}
				}, 1000);
			}else{
				WApplication.sp.set("runin", 3);
				nextTest();
			}
		}
	}
	
	private void updateData(){
		LogUtil.logMessage("wzb", "updateData");
		for(int i=0;i<17;i++){
			resultAdapter.getResultItem(i).setStatus(getStatus(i));
			resultAdapter.getResultItem(i).setResult(getResult(i));
			if(getResult(i).equals("pass")){
				resultAdapter.getResultItem(i).setColor(2);
			}else if(getResult(i).equals("fail")){
				resultAdapter.getResultItem(i).setColor(3);
			}
		}
		resultAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.logMessage("wzb", "resultactivity onPause");
		unregisterBr();
	}
	
	class ResultReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			final String action=intent.getAction();
			String status=resultFilter(intent.getStringExtra("status"));
			String result=resultFilter(intent.getStringExtra("result"));
			if(action.equals("custom.android.vibrator")){
				setStatus(6,status,result);
			}else if(action.equals("custom.android.memory")){
				setStatus(1,status,result);
			}else if(action.equals("custom.android.emmc")){
				setStatus(2,status,result);
			}else if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
				 int level = intent.getIntExtra("level", 0);
				 status="testing";
				 result=""+level;
				 setStatus(4, status, result);
				 resultAdapter.getResultItem(4).setStatus(status);
				 resultAdapter.getResultItem(4).setResult(result);
				 resultAdapter.notifyDataSetChanged();
			}
		}
	}
	
	
	
	private void setStatus(int id,String status,String result){
		WApplication.sp_result.set(WApplication.SPRESULT_S[id], status);
		WApplication.sp_result.set(WApplication.SPRESULT_R[id], result);
		resultAdapter.getResultItem(id).setStatus(status);
		resultAdapter.getResultItem(id).setResult(result);
		if(status.equals("testing")){
			resultAdapter.getResultItem(id).setColor(1);
		}else if(status.equals("done")){
			if(result.equals("pass")){
				resultAdapter.getResultItem(id).setColor(2);
			}else{
				resultAdapter.getResultItem(id).setColor(3);
			}
		}
		resultAdapter.notifyDataSetChanged();
	}
	
	private String resultFilter(String str){
		if(TextUtils.isEmpty(str)){
			str="None";
		}
		return str;
	}
	
	
	private void registerBr(){
		IntentFilter filter=new IntentFilter();
		filter.addAction("custom.android.vibrator");
		filter.addAction("custom.android.memory");
		filter.addAction("custom.android.emmc");
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		mResultReceiver=new ResultReceiver();
		registerReceiver(mResultReceiver, filter);
	}
	
	private void unregisterBr(){
		unregisterReceiver(mResultReceiver);
	}
	

	class ResultAdapter extends BaseAdapter {

		List<ResultItemBean> resultItems;

		public ResultAdapter(List<ResultItemBean> resultItems) {
			// TODO Auto-generated constructor stub
			this.resultItems = resultItems;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return resultItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return resultItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.result_list_item, null);
			}
			TextView result_item = (TextView) convertView.findViewById(R.id.result_item);
			result_item.setText(resultItems.get(position).getItemName());

			TextView result_status = (TextView) convertView.findViewById(R.id.result_item_status);
			result_status.setText(resultItems.get(position).getStatus());

			TextView result_result = (TextView) convertView.findViewById(R.id.result_item_result);
			result_result.setText(resultItems.get(position).getResult());

			// int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色

			// convertView.setBackgroundColor(colors[position % 2]);//
			// 每隔item之间颜色不同
			//test
			int backColor=getResultItem(position).getColor();
			if(backColor==1){
				convertView.setBackgroundColor(Color.BLUE);
			}else if(backColor==2){
				convertView.setBackgroundColor(Color.GREEN);
			}else if(backColor==3){
				convertView.setBackgroundColor(Color.RED);
			}else{
				convertView.setBackgroundColor(Color.WHITE);
			}
			return convertView;
		}

		public ResultItemBean getResultItem(int id) {
			return resultItems.get(id);
		}

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return true;
			return super.onKeyDown(keyCode, event);
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
