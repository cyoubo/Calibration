package com.calibration.activity;

import FileUtils.Utils.DirectoryUtils;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.component.PhotosPickingAdapter;
import com.example.calibration.R;
import com.system.GlobleParam;
import com.system.Initialization;
import com.system.IntentKey;
import com.system.SystemUtils;
import com.tool.JPGEFillter;
import com.tool.mydialog.ListDialog;

/**
 * 该界面用于完成待选解算影像或待重采样影像的选取<br>
 * 当启动intent中IsCalibration对应的值为true时，进行标定解算影像挑选（多选模式）<br>
 * 当启动intent中IsCalibration对应的值为false时，进行待重采样影像挑选（单选模式）<br>
 **/
public class APhotoPicking extends Activity implements Initialization
{
	
	private GridView gridView;
	private TextView tv_sure,tv_cancl;
	private PhotosPickingAdapter adapter;
	
	private boolean flag_IsCalibration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotopicking);
		Initialization();
		//判断启动intent的中的IsCalibration值
		flag_IsCalibration=getIntent().getExtras().getBoolean(IntentKey.IsCalibration.toString());
		
		String[] imagepaths=DirectoryUtils.GetSubFiles(SystemUtils.getPictureThumbnailPath(), new JPGEFillter(), true);
		adapter=new PhotosPickingAdapter(this,imagepaths,!flag_IsCalibration);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);
		
	}
	//gridviewitem按钮单击事件监听
	private OnItemClickListener itemClickListener=new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			// TODO Auto-generated method stub
			adapter.UpdatePicked(position);
		}
	};
	
	private OnClickListener tvClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//确定按钮事件监听
			if(v.getId()==R.id.aphotopicking_sure)
			{
				if(flag_IsCalibration)
					GlobleParam.Create().setThumbnailImagePath(adapter.getPickedImagePath());
				else 
					GlobleParam.Create().setRemapImagePath(adapter.getPickedImagePath()[0]);
				ListDialog dialog=new ListDialog(APhotoPicking.this, "挑選結果");
				dialog.SetList(DirectoryUtils.SpiltFullPathToNames(adapter.getPickedImagePath()), null);
				dialog.SetPlistener("取消", null);
				dialog.SetNlistener("確定", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if(flag_IsCalibration)
							startActivity(new Intent(APhotoPicking.this, APhotoFeatureDetect.class));
						else 
							startActivity(new Intent(APhotoPicking.this, APhotoRemap.class));
					}
				});
				dialog.Create().show();
			}
			//取消按钮事件监听
			else
			{
				adapter.ResetPicked();
			}
			
		}
	};
	
	
	
	@Override
	public void Initialization()
	{
		// TODO Auto-generated method stub
		gridView=(GridView)findViewById(R.id.aphotopicking_gridview);
		tv_sure=(TextView)findViewById(R.id.aphotopicking_sure);
		tv_cancl=(TextView)findViewById(R.id.aphotopicking_cancl);
		tv_cancl.setOnClickListener(tvClickListener);
		tv_sure.setOnClickListener(tvClickListener);
	}

}
