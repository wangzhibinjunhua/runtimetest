package com.wzb.runtimetest.test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 8, 2019 3:34:06 PM
 */
public class MyVideoView extends VideoView {

	public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);

		setMeasuredDimension(width, height);
	}

}
