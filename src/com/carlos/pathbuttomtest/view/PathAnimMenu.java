package com.carlos.pathbuttomtest.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.carlos.pathbuttomtest.R;
import com.carlos.pathbuttomtest.utils.MyRunnable;

/**
 * @author carlos carlosk@163.com
 * @version 创建时间：2012-5-25 上午10:28:45 类说明
 */

public class PathAnimMenu extends RelativeLayout {

	// 定义常量
	private static int MENULENGTH = 225;
	private static int OTHER_ITEM_HEIGHT;// 其他按钮的高
	private static int ADD_BTN_WIDTH;// 添加按钮的宽
	private static int FAR_LENGTH;// 远处的长度
	private static int END_LENGTH;// 结束的长度
	private static int NEAR_LENGTH;// 近处的长度

	private static final int DELAYED_TIME = 26;// 延迟动画的时间

	private static MyPoint STARTPOINT;
	private static RelativeLayout.LayoutParams STRARTLP;
	// private static int MenuTopMargt;// 距顶部的距离
	// private static int MenuLEFTMargt;// 距左边的距离
	// 定义成员变量
	private PathAnimMenuLinster pathAnimMenuLinster;// 当选中的时候调用这个
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			if (otherPathAnimItemList.size() > what) {
				PathAnimItem eachAnimItem = otherPathAnimItemList.get(what);
				if (isExpand) {
					Animation animation = getAnimationByOtherItemExpend((Integer) eachAnimItem
							.getTag());
					eachAnimItem.startAnimation(animation);
				} else {
					RotateAnimation animation1 = new RotateAnimation(0, 1080f,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					animation1.setDuration(80);
					Animation animation2 = getAnimationByOtherItemClosed((Integer) eachAnimItem
							.getTag());
					AnimationSet animationSet = new AnimationSet(getContext(),
							null);
					animationSet.addAnimation(animation1);
					animationSet.addAnimation(animation2);
					animationSet.setDuration(260);
					eachAnimItem.startAnimation(animationSet);
				}
			}
		}

	};
	private List<PathAnimItem> otherPathAnimItemList = new ArrayList<PathAnimItem>();
	private PathAnimItem addItem;
	private boolean isExpand;// 是扩展还是收拢

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
			PathAnimItem pathAnimItem = (PathAnimItem) v;
			// 让本view的动画扩大,淡出
			// 最后回到原位
			Animation alphaAnimation = new AlphaAnimation(1, 0);
			alphaAnimation.setDuration(500);
			AnimationSet animationSet = new AnimationSet(getContext(), null);
			animationSet.addAnimation(alphaAnimation);
			animationSet.addAnimation(getScaleAnim(
					(Integer) pathAnimItem.getTag(), 1f, 2f));
			pathAnimItem.startAnimation(animationSet);
			// 让其他view缩小,淡出
			int curentIndex = (Integer) pathAnimItem.getTag();
			int size = otherPathAnimItemList.size();
			for (int i = 0; i < size; i++) {
				if (curentIndex != i) {
					PathAnimItem tempAnimItem = otherPathAnimItemList.get(i);
					AnimationSet animationSet1 = new AnimationSet(getContext(),
							null);
					animationSet1.addAnimation(alphaAnimation);
					animationSet1.addAnimation(getScaleAnim(
							(Integer) tempAnimItem.getTag(), 1f, 0f));
					tempAnimItem.startAnimation(animationSet1);
				}
			}
			if (null != pathAnimMenuLinster) {
				pathAnimMenuLinster.didSelectedItem(pathAnimItem,
						(Integer) pathAnimItem.getTag());
			}
			isExpand = false;
		}
	};

	public PathAnimMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 添加所有的按钮..注意,所有的ivew必须定高,而且是正方形.整个layout必须要定高
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-28 下午2:40:23
	 * @param pathAnimItemList
	 * @param addItem
	 */
	public void addAllItems(List<PathAnimItem> pathAnimItemList,
			PathAnimItem addItem) {
		// 初始化各种参数
		initAllParams(pathAnimItemList.get(0));

		// 初始化其他按钮
		this.otherPathAnimItemList = pathAnimItemList;
		// 设置每一个的起始点,结束点,近点和远点
		int size = otherPathAnimItemList.size();
		// 每一个角度
		double angle = Math.PI / 180 * (90 / (size - 1));
		for (int i = 0; i < size; i++) {
			PathAnimItem eachAnimItem = otherPathAnimItemList.get(i);
			eachAnimItem.setStartPoint(STARTPOINT);

			double sin = Math.sin(angle * i);
			double cos = Math.cos(angle * i);
			// 结束的点
			eachAnimItem.setEndPoint(new MyPoint(STARTPOINT.x
					+ (int) (sin * END_LENGTH),
					(STARTPOINT.y - (int) (cos * END_LENGTH))));
			// 近处的点
			eachAnimItem.setNearPoint(new MyPoint(STARTPOINT.x
					+ (int) (sin * NEAR_LENGTH),
					(STARTPOINT.y - (int) (cos * NEAR_LENGTH))));
			// 远处的点
			eachAnimItem.setFarPoint(new MyPoint(STARTPOINT.x
					+ (int) (sin * FAR_LENGTH),
					(STARTPOINT.y - (int) (cos * FAR_LENGTH))));
			eachAnimItem.setOnClickListener(otherItemsAddClickListener);
			addView(eachAnimItem);
			eachAnimItem.setLayoutParams(getlpByMyPoint(eachAnimItem
					.getStartPoint()));

			eachAnimItem.setTag(i);
		}

		// 添加Add按钮
		this.addItem = addItem;
		addItem.setStartPoint(STARTPOINT);
		addItem.setOnClickListener(itemAddClickListener);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				ADD_BTN_WIDTH, ADD_BTN_WIDTH);
		lp.topMargin = STARTPOINT.y;
		lp.leftMargin = STARTPOINT.x;
		addItem.setLayoutParams(lp);
		addView(addItem);

		isExpand = false;
		setBackgroundColor(getResources().getColor(R.color.red));

		invalidate();
	}

	/**
	 * 初始化各种参数
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-28 下午2:33:47
	 * @param pathAnimItem
	 * @param addItem2
	 */
	private void initAllParams(PathAnimItem pathAnimItem) {
		MENULENGTH = getLayoutParams().height;
		OTHER_ITEM_HEIGHT = pathAnimItem.getViewHeight();
		ADD_BTN_WIDTH = OTHER_ITEM_HEIGHT - 8;
		// 把所有分成11分,远处是11,结束是10,近处是9
		FAR_LENGTH = MENULENGTH - 10 - ADD_BTN_WIDTH;
		// 结束点
		END_LENGTH = FAR_LENGTH / 11 * 10;
		// 近处
		NEAR_LENGTH = FAR_LENGTH / 11 * 9;

		// 开始按钮的坐标
		STARTPOINT = new MyPoint(10, MENULENGTH - ADD_BTN_WIDTH - 10);

		// 其他按钮的起始点
		STRARTLP = new RelativeLayout.LayoutParams(OTHER_ITEM_HEIGHT,
				OTHER_ITEM_HEIGHT);
		STRARTLP.topMargin = STARTPOINT.y;
		STRARTLP.leftMargin = STARTPOINT.x;
	}

	// 当扩展的时候,并且不点到任何地方的时候,才调用
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isExpand) {
			return false;
		}
		// 改成收拢
		isExpand = false;
		closedAnim();
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

		int size = otherPathAnimItemList.size();
		for (int i = 0; i < size; i++) {
			mHandler.postDelayed(new MyRunnable(i, mHandler), DELAYED_TIME
					* (size - 1 - i));
		}
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
		// 延迟扩展
		int size = otherPathAnimItemList.size();
		for (int i = 0; i < size; i++) {
			mHandler.postDelayed(new MyRunnable(i, mHandler), DELAYED_TIME * i);
		}
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
	 * @return
	 */
	protected Animation animRotate(float fromDegress, float toDegrees,
			float pivotXValue, float pivotYValue) {
		RotateAnimation animation = new RotateAnimation(fromDegress, toDegrees,
				Animation.RELATIVE_TO_SELF, pivotXValue,
				Animation.RELATIVE_TO_SELF, pivotYValue);
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
	private Animation getAnimationByOtherItemExpend(final int itemIndex) {
		PathAnimItem eachAnimItem = otherPathAnimItemList.get(itemIndex);
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
				PathAnimItem eachAnimItem = otherPathAnimItemList
						.get(itemIndex);
				eachAnimItem.clearAnimation();
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
						PathAnimItem eachAnimItem = otherPathAnimItemList
								.get(itemIndex);
						eachAnimItem.clearAnimation();
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
								PathAnimItem eachAnimItem = otherPathAnimItemList
										.get(itemIndex);
								eachAnimItem
										.setLayoutParams(getlpByMyPoint(eachAnimItem
												.getEndPoint()));
								eachAnimItem.clearAnimation();
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
	private Animation getAnimationByOtherItemClosed(final int itemIndex) {
		PathAnimItem eachAnimItem = otherPathAnimItemList.get(itemIndex);
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
				PathAnimItem eachAnimItem = otherPathAnimItemList
						.get(itemIndex);
				eachAnimItem.clearAnimation();
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
						PathAnimItem eachAnimItem = otherPathAnimItemList
								.get(itemIndex);
						eachAnimItem
								.setLayoutParams(getlpByMyPoint(eachAnimItem
										.getStartPoint()));
						eachAnimItem.clearAnimation();
					}
				});
				eachAnimItem.startAnimation(animation);
			}
		});
		return animation;
	}

	/**
	 * 扩大或者缩小的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-28 上午11:15:45
	 * @param pathAnimItem
	 * @param f
	 * @param g
	 * @return
	 */
	protected Animation getScaleAnim(final int itemIndex, float from, float to) {
		ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(500);
		scaleAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				PathAnimItem pathAnimItem = otherPathAnimItemList
						.get(itemIndex);
				pathAnimItem.clearAnimation();
				pathAnimItem.setLayoutParams(getlpByMyPoint(pathAnimItem
						.getStartPoint()));
			}
		});
		return scaleAnimation;
	}
}
