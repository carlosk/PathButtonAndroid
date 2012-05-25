package com.carlos.pathbuttomtest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.carlos.pathbuttomtest.view.PathAnimItem;
import com.carlos.pathbuttomtest.view.PathAnimMenu;

public class PathButtomTestActivity extends Activity {
	public static final String TAG = "PathButtomTestActivity";
	private PathAnimMenu pm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pm = (PathAnimMenu) findViewById(R.id.pm);
		Log.i(TAG, "高度为" + pm.getLayoutParams().height);
		List<PathAnimItem> otherAnimItemList = initOtherAnimItemList();

		// add按钮
		PathAnimItem addItem = new PathAnimItem(this, null);
		addItem.setBackgroundResource(R.drawable.friends_delete);

		pm.setSomeThing(otherAnimItemList, addItem);

	}

	/**
	 * 初始化其他按钮
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 下午2:36:01
	 * @return
	 */
	private List<PathAnimItem> initOtherAnimItemList() {
		List<PathAnimItem> result = new ArrayList<PathAnimItem>();

		PathAnimItem otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_camera);
		result.add(otherItem);

		// otherItem = new PathAnimItem(this, null);
		// otherItem.setBackgroundResource(R.drawable.composer_music);
		// result.add(otherItem);
		//
		// otherItem = new PathAnimItem(this, null);
		// otherItem.setBackgroundResource(R.drawable.composer_place);
		// result.add(otherItem);
		//
		// otherItem = new PathAnimItem(this, null);
		// otherItem.setBackgroundResource(R.drawable.composer_sleep);
		// result.add(otherItem);
		//
		// otherItem = new PathAnimItem(this, null);
		// otherItem.setBackgroundResource(R.drawable.composer_thought);
		// result.add(otherItem);
		//
		// otherItem = new PathAnimItem(this, null);
		// otherItem.setBackgroundResource(R.drawable.composer_with);
		// result.add(otherItem);

		return result;
	}

}