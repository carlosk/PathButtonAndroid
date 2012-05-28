package com.carlos.pathbuttomtest.utils;

import android.os.Handler;

/**
 * @author carlos carlosk@163.com
 * @version 创建时间：2012-5-28 下午2:12:21 类说明
 */

public class MyRunnable implements Runnable {

	public MyRunnable(int intData, Handler handler) {
		super();
		this.intData = intData;
		this.mHandler = handler;
	}

	private volatile int intData;
	private Handler mHandler;

	@Override
	public void run() {
		mHandler.sendEmptyMessage(intData);
	}

}
