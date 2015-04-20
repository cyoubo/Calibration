package com.calibration.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.calibration.beanshelper.CalibrationResultBeansHelper;
import com.component.CalibrationResultAdapter;
import com.example.calibration.R;
import com.opecvutils.CalibrationHelper;
import com.system.GlobleParam;
import com.system.Initialization;
import com.system.SystemUtils;
import com.tool.BitmapHelper;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;
import com.tool.mydialog.ListDialog;

/**
 * 用于完成影像的重采样
 * */
public class APhotoRemap extends Activity implements Initialization
{
	
	private TextView tv_remappath,tv_resolution,tv_paramindex,tv_outpath,tv_progress;
	private ImageView ig_plus;
	private ProgressBar progressBar;
	private Button btn_sure;
	private CalibrationResultAdapter adapter;
	
	private int width,height;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotoremap);
		Initialization();
		DisplayImageresolution();
		PrepareAdapter();
		
	}
	
	OnClickListener clickListener=new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			
			
		}
	};
	
	OnClickListener imageClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			ListDialog dialog=new ListDialog(APhotoRemap.this, "标定成果");
			dialog.SetAdapter(adapter, new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					tv_resolution.setText(adapter.getClickedItemResolution(which));
				}
			});
			dialog.Create().show();
			
		}
	};
	
	private void DisplayImageresolution()
	{
		BitmapHelper helper=new BitmapHelper(GlobleParam.Create().getRemapImagePath());
		Bitmap bitmap=helper.getBitmap();
		width=bitmap.getWidth();
		height=bitmap.getHeight();
		bitmap.recycle();
		
		tv_resolution.setText(String.format("%d*%d",width,height));
	}
	
	private void PrepareAdapter()
	{
		String field="Resolution";
		String value=String.format("%d*%d",width,height);
		SQLiteOrmSDContext context=new SQLiteOrmSDContext(this,new SystemUtils());
		adapter=new CalibrationResultAdapter(this, CalibrationResultBeansHelper.queryForEq(context, field, value));
	}
	
	
	@Override
	public void Initialization()
	{
		// TODO Auto-generated method stub
		tv_outpath=(TextView)findViewById(R.id.aphotoremap_tv_outpath);
		tv_remappath=(TextView)findViewById(R.id.aphotoremap_tv_remappath);
		tv_resolution=(TextView)findViewById(R.id.aphotoremap_tv_resolution);
		tv_paramindex=(TextView)findViewById(R.id.aphotoremap_tv_paramIndex);
		tv_progress=(TextView)findViewById(R.id.aphotoremap_tv_progress);
		
		ig_plus=(ImageView)findViewById(R.id.aphotoremap_ig_paramIndex);
		progressBar=(ProgressBar)findViewById(R.id.aphotoremap_progressBar);
		btn_sure=(Button)findViewById(R.id.aphotoremap_sure);
		
		btn_sure.setOnClickListener(clickListener);
		progressBar.setVisibility(View.INVISIBLE);
		ig_plus.setOnClickListener(imageClickListener);
		
		this.tv_remappath.setText(GlobleParam.Create().getRemapImagePath());
		this.tv_outpath.setText(SystemUtils.getPictureRemapPath());
		
	}

}
