package com.calibration.activity;

import org.opencv.core.Mat;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.calibration.beanshelper.CalibrationResultHelper;
import com.component.CalibrationResultAdapter;
import com.example.calibration.R;
import com.opecvutils.BitmapHelper_CV;
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
	private ImageView ig_plus,ig_remapimage;
	private ProgressBar progressBar;
	private Button btn_sure;
	private CalibrationResultAdapter adapter;
	
	private int width,height;
	private CalibrationResultHelper beansHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotoremap);
		Initialization();
		DisplayImageresolution();
		PrepareAdapter();
		
	}
	
	/**
	 * 确定按钮的事件监听，开启异步重采样任务
	 * */
	OnClickListener clickListener=new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			//创建异步任务
			RemapAsynTask remapAsynTask=new RemapAsynTask(GlobleParam.Create().getRemapImagePath());
			//执行异步操作
			remapAsynTask.execute(beansHelper);
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
					//获取选定的标定结果
					beansHelper =new CalibrationResultHelper(adapter.getClickedItem(which));
					//显示标定结果名称
					tv_paramindex.setText(beansHelper.getCalibrationLongName());
				}
			});
			dialog.Create().show();
			
		}
	};
	
	//更新图片分辨率的TextView
	private void DisplayImageresolution()
	{
		BitmapHelper helper=new BitmapHelper(GlobleParam.Create().getRemapImagePath());
		int[] size=helper.getImageSize();
		width=size[0];height=size[1];	
		tv_resolution.setText(String.format("%d*%d",width,height));
	}
	//准备标定结果Adapter
	private void PrepareAdapter()
	{
		String field="Resolution";
		String value=String.format("%d*%d",width,height);
		SQLiteOrmSDContext context=new SQLiteOrmSDContext(this,GlobleParam.Create());
		//adapter=new CalibrationResultAdapter(this, CalibrationResultBeansHelper.queryForEq(context, field, value));
		adapter=new CalibrationResultAdapter(this, CalibrationResultHelper.queryAll(context));
		Log.d("demo", "adapter= null is "+(adapter==null));
	}
	
	
	@Override
	public void Initialization()
	{
		tv_outpath=(TextView)findViewById(R.id.aphotoremap_tv_outpath);
		tv_remappath=(TextView)findViewById(R.id.aphotoremap_tv_remappath);
		tv_resolution=(TextView)findViewById(R.id.aphotoremap_tv_resolution);
		tv_paramindex=(TextView)findViewById(R.id.aphotoremap_tv_paramIndex);
		tv_progress=(TextView)findViewById(R.id.aphotoremap_tv_progress);
		
		ig_plus=(ImageView)findViewById(R.id.aphotoremap_ig_paramIndex);
		ig_remapimage=(ImageView)findViewById(R.id.aphotoremap_ig_remapimage);
		progressBar=(ProgressBar)findViewById(R.id.aphotoremap_progressBar);
		btn_sure=(Button)findViewById(R.id.aphotoremap_sure);
		
		btn_sure.setOnClickListener(clickListener);
		progressBar.setVisibility(View.INVISIBLE);
		ig_plus.setOnClickListener(imageClickListener);
		
		this.tv_remappath.setText(GlobleParam.Create().getRemapImagePath());
		this.tv_outpath.setText(SystemUtils.getPictureRemapPath());
	}
	
	/**
	 * 内部类：用于完成校正图像重采样的异步任务类
	 * **/
	private class RemapAsynTask extends AsyncTask<CalibrationResultHelper, String, Integer>
	{
		/**待重采样的图像路径*/
		private String imagepath;
		
		/**
		 * 构造函数<br>
		 * @param path 待重采样的图像路径
		 * */
		public RemapAsynTask(String path)
		{
			this.imagepath=path;
		}
		
		@Override
		protected void onPreExecute()
		{
			tv_progress.setText("处理中");
			progressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Integer doInBackground(CalibrationResultHelper... params)
		{
			//创建BitmapHelper_CV对象完成相关操作
			BitmapHelper_CV cvhelper=new BitmapHelper_CV(imagepath);
			//生成重采用图像矩阵
			Mat resultmat=cvhelper.getUndistoredMat(params[0].getCameraMatrix(), params[0].getDistMatrix());
			//创建BitmapHelper对象完成存储操作
			BitmapHelper helper=new BitmapHelper(BitmapHelper_CV.MatToBitmap(resultmat));
			//文件存储
			String imagepath=SystemUtils.getPictureRemapPath();
			String thumbnailpath=SystemUtils.getPictureRemapThumbnailPath();
			String name=params[0].getCalibrationShortName();
			helper.SaveintoFilewithThumbnail(imagepath,thumbnailpath,name, CompressFormat.JPEG);
			//异步更新展示校正图像缩略图
			publishProgress(thumbnailpath,name);
			
			return 1;
		}
		
		@Override
		protected void onProgressUpdate(String... values)
		{
			String temp=String.format("%s/%s.%s", values[0],values[1],CompressFormat.JPEG);
			BitmapHelper bitmapHelper=new BitmapHelper(temp);
			ig_remapimage.setImageBitmap(bitmapHelper.getBitmap());
		}
		
		@Override
		protected void onPostExecute(Integer result)
		{
			tv_progress.setText("处理结束");
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

}
