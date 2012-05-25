package com.carlos.pathbuttomtest.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author carlos carlosk@163.com
 * @version 创建时间：2012-5-25 上午10:28:45 类说明
 */

public class PathAnimMenu extends RelativeLayout {

	// 定义常量
	private static final MyPoint STARTPOINT = new MyPoint(50, 420);
	private static final RelativeLayout.LayoutParams STRARTLP = new RelativeLayout.LayoutParams(
			50, 50);
	static {
		STRARTLP.topMargin = STARTPOINT.y;
		STRARTLP.leftMargin = STARTPOINT.x;
	}
	private static final int ENDLENGTH = 100;// 结束的长度
	private static final int FARLENGTH = 120;// 远处的长度
	private static final int NEARLENGTH = 80;// 近处的长度
	// 定义成员变量
	private PathAnimMenuLinster pathAnimMenuLinster;// 当选中的时候调用这个
	private Handler mHandler = new Handler();
	private List<PathAnimItem> otherPathAnimItemList = new ArrayList<PathAnimItem>();
	private PathAnimItem addItem;
	private boolean isExpand;// 是扩展还是收拢
	private boolean isAnim;// 是否正在动画 TODO

	// +按钮的监听器
	private View.OnClickListener itemAddClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// 加入动画
			isExpand = !isExpand;
			if (isExpand) {
				expendAnim();
			} else {
				closedAnim();
			}
		}
	};
	// 其他按钮的监听器
	private View.OnClickListener otherItemsAddClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			// 加入动画
			// 让本view的动画扩大,淡出
			// 让其他view缩小,淡出
			// 最后回到原位
			if (null != pathAnimMenuLinster) {
				PathAnimItem pathAnimItem = (PathAnimItem) v;
				pathAnimMenuLinster.didSelectedItem(pathAnimItem);
			}
		}
	};

	public PathAnimMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PathAnimMenu(Context context, List<PathAnimItem> pathAnimItemList,
			PathAnimItem addItem) {
		super(context);
		// 初始化其他按钮
		this.otherPathAnimItemList = pathAnimItemList;
		// 设置每一个的起始点,结束点,近点和远点
		for (PathAnimItem eachAnimItem : otherPathAnimItemList) {
			eachAnimItem.setStartPoint(STARTPOINT);
			// TODO
			eachAnimItem.setLayoutParams(STRARTLP);
			eachAnimItem.setOnClickListener(otherItemsAddClickListener);
		}
		// 添加Add按钮
		this.addItem = addItem;
		addItem.setStartPoint(STARTPOINT);
		addItem.setLayoutParams(STRARTLP);
		addItem.setOnClickListener(itemAddClickListener);
		isExpand = false;
		
	}

	// 当扩展的时候,并且不点到任何地方的时候,才调用
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isExpand) {
			return false;
		}
		// 改成收拢
		isExpand = false;
		if (isExpand) {
			expendAnim();
		} else {
			closedAnim();
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 展开的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 上午11:31:22
	 */
	private void closedAnim() {
		// TODO Auto-generated method stub

	}

	/**
	 * 合拢的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 上午11:31:19
	 */
	private void expendAnim() {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置监听器
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 上午10:38:23
	 * @param pathAnimMenuLinster
	 */
	public void setPathAnimMenuLinster(PathAnimMenuLinster pathAnimMenuLinster) {
		this.pathAnimMenuLinster = pathAnimMenuLinster;
	}

}
