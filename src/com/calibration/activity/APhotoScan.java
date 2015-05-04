package com.calibration.activity;

import com.component.PhotoThumbAdapter;
import com.example.calibration.R;
import com.system.IntentKey;
import com.system.SystemUtils;
import com.tool.JPGEFillter;

import FileUtils.Utils.DirectoryUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/*** 该Activity用于完成影像的GridVIew浏览界面处理 <br>
 * 适用情况
 * <ol>
 * <li>拍照中查看已拍摄影像，即启动intent中Isremap为false</li>
 * <li>选择查看重绘图像，即启动intent中Isremap为true</li>
 * </ol>
 * */
public class APhotoScan extends Activity
{
	private GridView grid;
	private PhotoThumbAdapter adapter;
	
	private boolean flag_isremap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotoscan);
		//获得展示标识，用于确定数据源
		flag_isremap=getIntent().getExtras().getBoolean(IntentKey.Isremap.toString());
		
		initialization();
	}

	private void initialization()
	{
		// 绑定控件
		grid = (GridView) findViewById(R.id.aphotoscan_gridview);
		
		String[] paths=null;
		if(flag_isremap)
		{
			//若为重画则获取重画缩略图文件路径
			paths = DirectoryUtils.GetSubFiles(
				SystemUtils.getPictureRemapThumbnailPath(), new JPGEFillter(), true);
		}
		else
		{
			//若不为重画则获取影像缩略图文件路径
			paths = DirectoryUtils.GetSubFiles(
					SystemUtils.getPictureThumbnailPath(), new JPGEFillter(), true);
		}
			

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
