package com.calibration.Activity;

import com.component.JPGEFillter;
import com.component.PhotoThumbAdapter;
import com.example.calibration.R;
import com.system.IntentKey;
import com.system.SystemUtils;

import FileUtils.Utils.DirectoryUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/*** 该Activity用于完成影像的GridVIew浏览界面处理 */
public class APhotoScan extends Activity
{
	private GridView grid;
	private PhotoThumbAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotoscan);

		initialization();
	}

	private void initialization()
	{
		// 绑定控件
		grid = (GridView) findViewById(R.id.aphotoscan_gridview);
		// 初始化adapter,獲取重採樣圖像縮略圖路徑
		String[] paths = DirectoryUtils.GetSubFiles(
				SystemUtils.getPictureRemapThumbnailPath(), new JPGEFillter(), true);

		if(paths!=null)
		{
			String[] names = new String[paths.length];
			for (int i = 0; i < names.length; i++)
			{
				names[i] = DirectoryUtils.SpiltFullPath(paths[i])[1];
			}
			adapter = new PhotoThumbAdapter(paths, names, this);

			grid.setAdapter(adapter);
			grid.setOnItemClickListener(griditemClickListener);
		}
	}

	private OnItemClickListener griditemClickListener = new OnItemClickListener()
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			// 将缩略图路径传递给APhotoScanInfo
			Intent intent = new Intent(APhotoScan.this, APhotoScanDisplay.class);
			intent.putExtra(IntentKey.PhotoThumbnailPath.toString(),
					adapter.getPhotoThumbnailPath(arg2));
			startActivity(intent);
		}
	};
}
