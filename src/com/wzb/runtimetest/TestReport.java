package com.wzb.runtimetest;

import java.util.ArrayList;
import java.util.List;

import com.wzb.runtimetest.TestResultActivity2.ResultAdapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date May 22, 2019 5:01:29 PM	
 */
public class TestReport extends BaseActivity implements OnScrollListener{
	
	private Context mContext;

	private ReportAdapter reportAdapter;

	private ListView reportListView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		mContext = TestReport.this;
		initView();
	}
	
	private void initView(){
		reportListView = (ListView) findViewById(R.id.lv_report);
		initAdapter();
		reportListView.setAdapter(reportAdapter);
		reportListView.setOnScrollListener(this);
	}
	
	private void initAdapter(){
		List<ResultItemBean> reportItems = new ArrayList<ResultItemBean>();
		for (int i = 0; i < 18; i++) {
			ResultItemBean items = new ResultItemBean(WApplication.ITEMNAME[i], "", getResult(i));
			if (getResult(i).equals("pass")) {
				items.setColor(2);
			} else if (getResult(i).equals("fail")) {
				items.setColor(3);
			}
			reportItems.add(items);
		}

		reportAdapter = new ReportAdapter(reportItems);
	}
	
	private String getResult(int id) {
		//return WApplication.sp_result.get(WApplication.SPRESULT_R[id], "None");
		String result=PropUtil.readData("/cache/recovery/last_runtimetest.prop", WApplication.SPRESULT_R[id]);
		if(TextUtils.isEmpty(result)){
			result="None";
		}
		return result;
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	class ReportAdapter extends BaseAdapter {

		List<ResultItemBean> reportItems;

		public ReportAdapter(List<ResultItemBean> reportItems) {
			// TODO Auto-generated constructor stub
			this.reportItems = reportItems;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return reportItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return reportItems.get(position);
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
			result_item.setText(reportItems.get(position).getItemName());

			TextView result_status = (TextView) convertView.findViewById(R.id.result_item_status);
			result_status.setText(reportItems.get(position).getStatus());

			TextView result_result = (TextView) convertView.findViewById(R.id.result_item_result);
			result_result.setText(reportItems.get(position).getResult());

			// int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色

			// convertView.setBackgroundColor(colors[position % 2]);//
			// 每隔item之间颜色不同
			// test
			int backColor = getResultItem(position).getColor();
			if (backColor == 1) {
				convertView.setBackgroundColor(Color.BLUE);
			} else if (backColor == 2) {
				convertView.setBackgroundColor(Color.GREEN);
			} else if (backColor == 3) {
				convertView.setBackgroundColor(Color.RED);
			} else {
				convertView.setBackgroundColor(Color.WHITE);
			}
			return convertView;
		}

		public ResultItemBean getResultItem(int id) {
			return reportItems.get(id);
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
