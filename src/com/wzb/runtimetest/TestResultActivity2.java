package com.wzb.runtimetest;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result2);
		mContext = TestResultActivity2.this;
		initView();

	}

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
		updateData();
	}
	
	private void updateData(){
		for(int i=0;i<17;i++){
			resultAdapter.getResultItem(i).setStatus(getStatus(i));
			resultAdapter.getResultItem(i).setResult(getResult(i));
		}
		resultAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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
			return true;
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
