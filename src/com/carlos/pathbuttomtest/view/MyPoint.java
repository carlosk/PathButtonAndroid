package com.carlos.pathbuttomtest.view;

/**
 * @author carlos carlosk@163.com
 * @version 创建时间：2012-5-25 上午10:34:26 类说明
 * 坐标
 */

public class MyPoint {
	public int x;
	public int y;

	public MyPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public MyPoint() {
		super();
	}

	@Override
	public String toString() {
		return "MyPoint [x=" + x + ", y=" + y + "]";
	}

}
