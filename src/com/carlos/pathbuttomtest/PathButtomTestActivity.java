package com.carlos.pathbuttomtest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;

public class PathButtomTestActivity extends Activity {
	public static final String TAG = "PathButtomTestActivity";

	private Button btnMusic;
	private Button btnCamera;
	private Button btnPlace;
	private Button btnFriendsDelete;
	private Button btnSleep;
	private Button btnThought;
	private Button btnWith;
	private List<Button> allBtnList = new ArrayList<Button>();

	private int height = 0;
	private int width = 0;
	private boolean isClick;
	private LayoutParams lParams = new LayoutParams(0, 0);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	/**
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-21 下午3:36:21
	 */
	private void initView() {
		btnMusic = (Button) findViewById(R.id.btnMusic);
		allBtnList.add(btnMusic);
		btnCamera = (Button) findViewById(R.id.btnCamera);
		allBtnList.add(btnCamera);
		btnFriendsDelete = (Button) findViewById(R.id.btnFriendsDelete);
		btnPlace = (Button) findViewById(R.id.btnPlace);
		allBtnList.add(btnPlace);
		btnWith = (Button) findViewById(R.id.btnWith);
		allBtnList.add(btnWith);
		btnSleep = (Button) findViewById(R.id.btnSleep);
		allBtnList.add(btnSleep);
		btnThought = (Button) findViewById(R.id.btnThought);
		allBtnList.add(btnThought);

		// 获取整个屏幕的宽和高
		Display display = getWindowManager().getDefaultDisplay();
		height = display.getHeight();
		width = display.getWidth();
		Log.d(TAG, "height = " + height + ";width=" + width);
		// 初始的位置是左边底部
		lParams.height = 50;
		lParams.width = 50;
		lParams.setMargins(10, height - 98, 0, 0);
		btnMusic.setLayoutParams(lParams);
		btnPlace.setLayoutParams(lParams);
		btnCamera.setLayoutParams(lParams);
		btnSleep.setLayoutParams(lParams);
		btnWith.setLayoutParams(lParams);
		btnThought.setLayoutParams(lParams);
		// 转动按钮应该是在最前面
		btnFriendsDelete.setLayoutParams(lParams);
		isClick = false;
		btnFriendsDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				isClick = !isClick;
				if (isClick) {
					btnFriendsDelete.startAnimation(animRotate(0, -45.0f, 0.5f,
							0.45f));
					btnCamera.startAnimation(animTranslate(0.0f, -180.0f, 10,
							height - 180 - 98 - 10, btnCamera, 80));
					btnMusic.startAnimation(animTranslate(60.0f, -140.0f, 60,
							height - 98 - 140 - 25, btnMusic, 140));
					btnPlace.startAnimation(animTranslate(100.0f, -120.0f,
							60 + 50, height - 98 - 130, btnPlace, 200));
					btnSleep.startAnimation(animTranslate(140.0f, -80.0f, 155,
							height - 98 - 90, btnSleep, 240));

					btnThought.startAnimation(animTranslate(180.0f, -40, 188,
							height - 98 - 47, btnThought, 280));

					btnWith.startAnimation(animTranslate(220.0f, 0, 220,
							height - 98, btnWith, 300));
				} else {
					btnFriendsDelete.startAnimation(animRotate(-45f, 0.0f,
							0.5f, 0.45f));
					btnCamera.startAnimation(animTranslate(0.0f, 190.0f, 10,
							height - 98, btnCamera, 80));
					btnMusic.startAnimation(animTranslate(-55.0f, 150.0f, 10,
							height - 98, btnMusic, 120));
					btnPlace.startAnimation(animTranslate(-110.0f, 130.0f, 10,
							height - 98, btnPlace, 140));
					btnSleep.startAnimation(animTranslate(-130.0f, 100.0f, 10,
							height - 98, btnSleep, 160));

					btnThought.startAnimation(animTranslate(-170.0f, 40.0f, 10,
							height - 98, btnThought, 180));

					btnWith.startAnimation(animTranslate(-230.0f, 0, 10,
							height - 98, btnWith, 200));
				}

			}
		});

		btnCamera.setOnClickListener(btnOnClickListener);
		btnPlace.setOnClickListener(btnOnClickListener);
		btnMusic.setOnClickListener(btnOnClickListener);
		btnSleep.setOnClickListener(btnOnClickListener);
		btnThought.setOnClickListener(btnOnClickListener);
		btnWith.setOnClickListener(btnOnClickListener);
	}

	private View.OnClickListener btnOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			for (Button eachButton : allBtnList) {
				if (eachButton.getId() == v.getId()) {
					eachButton.startAnimation(setAnimScale(2.5f, 2.5f));
				} else {
					eachButton.startAnimation(setAnimScale(0f, 0f));
				}
			}
		}
	};

	/**
	 * 做缩放动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-21 下午5:47:54
	 * @param f
	 * @param g
	 * @return
	 */
	protected Animation setAnimScale(float toX, float toY) {
		ScaleAnimation scaleAnimation = new ScaleAnimation(1f, toX, 1f, toY,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(500);
		scaleAnimation.setInterpolator(this,
				android.R.anim.accelerate_decelerate_interpolator);
		return scaleAnimation;
	}

	/**
	 * 移动的动画
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-21 下午4:00:16
	 * @param f
	 * @param g
	 * @param i
	 * @param j
	 * @param btnCamera2
	 * @param k
	 * @return
	 */
	protected Animation animTranslate(float toX, float toY, final int lastX,
			final int lastY, final Button button, long durationMillis) {
		TranslateAnimation anTransformation = new TranslateAnimation(0, toX, 0,
				toY);
		anTransformation.setDuration(durationMillis);
		anTransformation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				LayoutParams params = new LayoutParams(0, 0);
				params.height = 50;
				params.width = 50;
				params.setMargins(lastX, lastY, 0, 0);
				button.setLayoutParams(params);
				button.clearAnimation();
			}
		});
		return anTransformation;
	}

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
}