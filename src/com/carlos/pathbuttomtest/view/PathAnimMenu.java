package com.carlos.pathbuttomtest.view;

import java.util.ArrayList;
import java.util.List;

import com.carlos.pathbuttomtest.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

/**
 * @author carlos carlosk@163.com
 * @version 创建时间：2012-5-25 上午10:28:45 类说明
 */

public class PathAnimMenu extends RelativeLayout {

	// 定义常量
	private static final int MENULENGTH = 225;
	private static final MyPoint STARTPOINT = new MyPoint(10,
			MENULENGTH - 38 - 10);
	private static final RelativeLayout.LayoutParams STRARTLP = new RelativeLayout.LayoutParams(
			50, 50);
	static {
		STRARTLP.topMargin = STARTPOINT.y;
		STRARTLP.leftMargin = STARTPOINT.x;
	}
	private static final int FARLENGTH = MENULENGTH - 58 - 10;// 远处的长度
	private static final int ENDLENGTH = MENULENGTH - 58 - 10 - 30;// 结束的长度
	private static final int NEARLENGTH = MENULENGTH - 58 - 10 - 60;// 近处的长度

	private static int MenuTopMargt;// 距顶部的距离
	private static int MenuLEFTMargt;// 距左边的距离
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
			Toast.makeText(getContext(), "点击了其他按钮", 1).show();
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

	public void setSomeThing(List<PathAnimItem> pathAnimItemList,
			PathAnimItem addItem) {
		MenuTopMargt = 800 - ((RelativeLayout.LayoutParams) getLayoutParams()).height;
		MenuLEFTMargt = ((RelativeLayout.LayoutParams) getLayoutParams()).leftMargin;
		// 初始化其他按钮
		this.otherPathAnimItemList = pathAnimItemList;
		// 设置每一个的起始点,结束点,近点和远点
		int size = otherPathAnimItemList.size();
		for (int i = 0; i < size; i++) {
			PathAnimItem eachAnimItem = otherPathAnimItemList.get(i);
			eachAnimItem.setStartPoint(STARTPOINT);
			// TODO
			// 结束的点
			eachAnimItem.setEndPoint(new MyPoint(STARTPOINT.x + 0,
					(STARTPOINT.y - ENDLENGTH)));
			// 近处的点
			eachAnimItem.setNearPoint(new MyPoint(STARTPOINT.x + 0,
					(STARTPOINT.y - NEARLENGTH)));
			// 远处的点
			eachAnimItem.setFarPoint(new MyPoint(STARTPOINT.x + 0,
					(STARTPOINT.y - FARLENGTH)));
			eachAnimItem.setOnClickListener(otherItemsAddClickListener);
			addView(eachAnimItem);
			eachAnimItem.setLayoutParams(getlpByMyPoint(eachAnimItem
					.getStartPoint()));
		}
		// 添加Add按钮
		this.addItem = addItem;
		addItem.setStartPoint(STARTPOINT);
		addItem.setOnClickListener(itemAddClickListener);
		addItem.setLayoutParams(STRARTLP);
		addView(addItem);
		isExpand = false;
		setBackgroundColor(getResources().getColor(R.color.red));

		invalidate();
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
	 * 
	 * 合拢的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 上午11:31:22
	 */
	private void closedAnim() {
		// 旋转 add按钮
		addItem.startAnimation(animRotate(-45f, 0.0f, 0.5f, 0.45f));
		PathAnimItem eachAnimItem = otherPathAnimItemList.get(0);
		Animation animation = getAnimationByOtherItemClosed(eachAnimItem);
		eachAnimItem.startAnimation(animation);
	}

	/**
	 * 展开的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 上午11:31:19
	 */
	private void expendAnim() {
		// 旋转 add按钮
		addItem.startAnimation(animRotate(0, -45.0f, 0.5f, 0.45f));
		PathAnimItem eachAnimItem = otherPathAnimItemList.get(0);
		Animation animation = getAnimationByOtherItemExpend(eachAnimItem);
		eachAnimItem.startAnimation(animation);
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

	/**
	 * 根据坐标返回LP属性
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 下午3:07:40
	 * @param myPoint
	 * @return
	 */
	private RelativeLayout.LayoutParams getlpByMyPoint(MyPoint myPoint) {
		RelativeLayout.LayoutParams result = new RelativeLayout.LayoutParams(
				50, 50);
		result.topMargin = myPoint.y;
		result.leftMargin = myPoint.x;
		return result;
	}

	/**
	 * 
	 * 动画效果
	 * 
	 * 
	 */
	/**
	 * 转动动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-21 下午3:49:49
	 * @param f
	 * @param g
	 * @param h
	 * @return
	 */
	protected Animation animRotate(float fromDegress, float toDegrees,
			float pivotXValue, float pivotYValue) {
		RotateAnimation animation = new RotateAnimation(fromDegress, toDegrees,
				Animation.RELATIVE_TO_SELF, pivotXValue,
				Animation.RELATIVE_TO_SELF, pivotYValue);
		// animation.setDuration(1000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animation.setFillAfter(true);
			}
		});
		return animation;
	}

	/**
	 * 移动的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-21 下午4:00:16
	 * @return
	 */
	protected Animation animTranslate(MyPoint fromPoint, MyPoint toPoint,
			long durationMillis) {
		TranslateAnimation anTransformation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, toPoint.x
						- fromPoint.x, Animation.RELATIVE_TO_SELF, 0,
				Animation.ABSOLUTE, toPoint.y - fromPoint.y);
		anTransformation.setDuration(durationMillis);
		return anTransformation;
	}

	/**
	 * 让其他按钮扩展的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 下午5:33:19
	 * @param eachAnimItem
	 * @return
	 */
	private Animation getAnimationByOtherItemExpend(
			final PathAnimItem eachAnimItem) {
		Animation animation = animTranslate(eachAnimItem.getStartPoint(),
				eachAnimItem.getFarPoint(), 180);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				eachAnimItem.setLayoutParams(getlpByMyPoint(eachAnimItem
						.getFarPoint()));

				animation = animTranslate(eachAnimItem.getFarPoint(),
						eachAnimItem.getNearPoint(), 180);
				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						eachAnimItem
								.setLayoutParams(getlpByMyPoint(eachAnimItem
										.getNearPoint()));
						animation = animTranslate(eachAnimItem.getNearPoint(),
								eachAnimItem.getEndPoint(), 180);
						animation.setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {

							}

							@Override
							public void onAnimationRepeat(Animation animation) {

							}

							@Override
							public void onAnimationEnd(Animation animation) {
								 animation.setFillAfter(true);
							}
						});
						eachAnimItem.startAnimation(animation);
					}
				});
				eachAnimItem.startAnimation(animation);
			}
		});
		return animation;
	}

	/**
	 * 让其他按钮收拢的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 下午5:33:19
	 * @param eachAnimItem
	 * @return
	 */
	private Animation getAnimationByOtherItemClosed(
			final PathAnimItem eachAnimItem) {
		eachAnimItem
				.setLayoutParams(getlpByMyPoint(eachAnimItem.getEndPoint()));
		Animation animation = animTranslate(eachAnimItem.getEndPoint(),
				eachAnimItem.getFarPoint(), 180);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				eachAnimItem.setLayoutParams(getlpByMyPoint(eachAnimItem
						.getFarPoint()));

				animation = animTranslate(eachAnimItem.getFarPoint(),
						eachAnimItem.getStartPoint(), 180);
				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						eachAnimItem
								.setLayoutParams(getlpByMyPoint(eachAnimItem
										.getStartPoint()));
					}
				});
				eachAnimItem.startAnimation(animation);
			}
		});
		return animation;
	}
}
