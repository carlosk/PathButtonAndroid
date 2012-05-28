package com.carlos.pathbuttomtest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.carlos.pathbuttomtest.view.PathAnimItem;
import com.carlos.pathbuttomtest.view.PathAnimMenu;
import com.carlos.pathbuttomtest.view.PathAnimMenuLinster;

public class PathButtomTestActivity extends Activity {
	public static final String TAG = "PathButtomTestActivity";
	private PathAnimMenu pm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pm = (PathAnimMenu) findViewById(R.id.pm);
		List<PathAnimItem> otherAnimItemList = initOtherAnimItemList();

		// add按钮
		PathAnimItem addItem = new PathAnimItem(this, null);
		addItem.setBackgroundResource(R.drawable.friends_delete);

		pm.addAllItems(otherAnimItemList, addItem);
		pm.setPathAnimMenuLinster(new PathAnimMenuLinster() {

			@Override
			public void didSelectedItem(PathAnimItem item, int index) {
				Toast.makeText(PathButtomTestActivity.this,
						"点击了" + (index + 1), Toast.LENGTH_SHORT).show();
			}
		});

	}

	/**
	 * 初始化其他按钮
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-5-25 下午2:36:01
	 * @return
	 */
	private List<PathAnimItem> initOtherAnimItemList() {
		List<PathAnimItem> resultList = new ArrayList<PathAnimItem>();

		PathAnimItem otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_camera);
		resultList.add(otherItem);

		otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_music);
		resultList.add(otherItem);

		otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_place);
		resultList.add(otherItem);

		otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_sleep);
		resultList.add(otherItem);

		otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_thought);
		resultList.add(otherItem);

		otherItem = new PathAnimItem(this, null);
		otherItem.setBackgroundResource(R.drawable.composer_with);
		resultList.add(otherItem);

		return resultList;
	}

}