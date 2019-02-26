package com.wzb.runtimetest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.conn.tsccm.WaitingThread;

import com.wzb.runtimetest.util.LogUtil;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 25, 2019 5:25:03 PM	
 */
public class TestItemActivity extends BaseActivity implements OnScrollListener{
	
	
	private Context mContext;
	
	private ItemAdapter itemAdapter;

	private ListView itemListView = null;
	
	public final static String[] SPITEM={"reboot_s","memory_s","emmc_s","receiver_s"
			,"battery_s","audio_s","vibrator_s","camera_s","video_s","lcd_s","gps_s"
			,"bt_s","wifi_s","light_s","proximity_s","gravity_s","full_battery_s"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testitem);
		mContext=TestItemActivity.this;
		initView();
	}
	
	private void updateSp(int id,Boolean v){
		WApplication.sp.set(SPITEM[id], v);
	}
	
	private void initView(){
		itemListView=(ListView)findViewById(R.id.lv_testitem);
		initAdapter();
		itemListView.setAdapter(itemAdapter);
		itemListView.setOnScrollListener(this);
		itemListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				itemAdapter.getTestItem(arg2).cbToggle();
				itemAdapter.notifyDataSetChanged();
				updateSp(arg2,itemAdapter.getTestItem(arg2).getItemSelect());
				LogUtil.logMessage("wzb", "cb:" + arg2 + " " + itemAdapter.getTestItem(arg2).getItemSelect());
			}
		});
	}
	
	private void initAdapter(){
		List<TestItemBean> testItems = new ArrayList<TestItemBean>();
		TestItemBean item = new TestItemBean("Reboot",WApplication.sp.get("reboot_s", true));
		testItems.add(item);
		item = new TestItemBean("Memory",WApplication.sp.get("memory_s", true));
		testItems.add(item);
		item = new TestItemBean("EMMC",WApplication.sp.get("emmc_s", true));
		testItems.add(item);
		item = new TestItemBean("Receiver",WApplication.sp.get("receiver_s", true));
		testItems.add(item);
		item = new TestItemBean("Battery",WApplication.sp.get("battery_s", true));
		testItems.add(item);
		item = new TestItemBean("Audio",WApplication.sp.get("audio_s", true));
		testItems.add(item);
		item = new TestItemBean("Vibrator",WApplication.sp.get("vibrator_s", true));
		testItems.add(item);
		item = new TestItemBean("Camera",WApplication.sp.get("camera_s", true));
		testItems.add(item);
		item = new TestItemBean("Video",WApplication.sp.get("video_s", true));
		testItems.add(item);
		item = new TestItemBean("LCD",WApplication.sp.get("lcd_s", true));
		testItems.add(item);
		item = new TestItemBean("GPS",WApplication.sp.get("gps_s", true));
		testItems.add(item);
		item = new TestItemBean("BT",WApplication.sp.get("bt_s", true));
		testItems.add(item);
		item = new TestItemBean("WIFI",WApplication.sp.get("wifi_s", true));
		testItems.add(item);
		item = new TestItemBean("Light",WApplication.sp.get("light_s", true));
		testItems.add(item);
		item = new TestItemBean("Proximity",WApplication.sp.get("proximity_s", true));
		testItems.add(item);
		item = new TestItemBean("Gravity",WApplication.sp.get("gravity_s", true));
		testItems.add(item);
		item = new TestItemBean("Full Battery",WApplication.sp.get("full_battery_s", true));
		testItems.add(item);
		
		itemAdapter=new ItemAdapter(testItems);
	}
	
	class ItemAdapter extends BaseAdapter{
		
		List<TestItemBean> testItems;

		public ItemAdapter(List<TestItemBean> testItems) {
			// TODO Auto-generated constructor stub
			this.testItems = testItems;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return testItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return testItems.get(position);
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
				convertView = getLayoutInflater().inflate(R.layout.test_list_item, null);
			}
			TextView itemtv=(TextView)convertView.findViewById(R.id.tv_item_name);
			CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.cb_item);
			itemtv.setText(testItems.get(position).getItemName());
			checkBox.setChecked(testItems.get(position).getItemSelect());
			
			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色

			convertView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同
			return convertView;
		}
		
		public TestItemBean getTestItem(int id){
			return testItems.get(id);
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
