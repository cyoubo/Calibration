package com.calibration.Activity;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import FileUtils.Utils.DirectoryUtils;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.component.FeatureDetectingAdapter;
import com.example.calibration.R;
import com.opecvutils.CalibrationHelper;
import com.opecvutils.MatOutputer;
import com.system.GlobleParam;
import com.system.Initialization;
import com.system.SystemUtils;
import com.tool.mydialog.NormalDialog;
/**
 * 该界面用于待解算影像的角点亚像素级坐标定位
 * */
public class APhotoFeatureLocation extends Activity implements Initialization
{
	private ListView listView;
	private Button btn_check,btn_next;
	private ProgressBar progressBar;
	private ImageView imageView;
	
	private FeatureDetectingAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotofeaturelocation);
		Initialization();
	}
	
	//检测用事件响应
	private OnClickListener checkClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//异步执行特征点提取
			FeatureLocationAsyncTask task=new FeatureLocationAsyncTask(APhotoFeatureLocation.this);
			task.execute(GlobleParam.Create().getThumbnailImagePath());
			
		}
	};
	
	//下一步事件响应	
	private OnClickListener nextClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			
		}
	};
	
	@Override
	public void Initialization()
	{
		// TODO Auto-generated method stub
		listView=(ListView)findViewById(R.id.aphotofeaturelocation_listView1);
		btn_check=(Button)findViewById(R.id.aphotofeaturelocation_calibrate);
		btn_next=(Button)findViewById(R.id.aphotofeaturelocation_next);
		btn_check.setOnClickListener(checkClickListener);
		btn_next.setOnClickListener(nextClickListener);
		progressBar=(ProgressBar)findViewById(R.id.aphotofeaturelocation_ProgressBar);
		imageView=(ImageView)findViewById(R.id.aphotofeaturelocation_ImageView);
		
		

		String[] paths=DirectoryUtils.SpiltFullPathToNames(GlobleParam.Create().getThumbnailImagePath());
		adapter=new FeatureDetectingAdapter(this, paths);
		listView.setAdapter(adapter);
		
	}
	
	/**
	 * 内部类：<br>
	 * 完成异步的特征点提取的异步任务类型
	 * */
	public class FeatureLocationAsyncTask extends AsyncTask<String, Integer, Double>
	{
		/**上下文环境*/
		private Context context;
		/**影像尺寸*/
		private Size size;
		
		/**
		 * 构造函数
		 * @author Administrator
		 * @param context 上下文环境
		 * */
		private FeatureLocationAsyncTask(Context context)
		{
			this.context=context;
		}
		/**
		 * 获取影像尺寸
		 * */
		public Size getSize()
		{
			return size;
		}
		/**
		 * 设置影像尺寸
		 * */
		public void setSize(Size size)
		{
			this.size = size;
		}
		
		/**
		 * 异步操作前的提示操作
		 * */
		@Override
		protected void onPreExecute()
		{
			Toast.makeText(context, "开始处理", Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * 异步执行的操作
		 * @author Administrator
		 * @param params 待处理的特征影像的路径数组
		 * @return 提取后满足要求的影像张数
		 * */
		@Override
		protected Double doInBackground(String... params)
		{
			//構建標定工具
			CalibrationHelper helper=new CalibrationHelper(new Size(9, 6));
			helper.setImagePaths(GlobleParam.Create().getThumbnailImagePath());
			//獲取影像的亞像素級角點座標
			adapter.PreProgress();//开启处理
			for(int i=0;i<params.length;i++)
			{
				//若提取成功则返回2 标示处理成功，否则返回3 表示处理失败
				int flag=helper.FeatureLocated(i)?2:3;
				publishProgress(i,flag);//修改UI
			}
			//標定處理
			publishProgress(-1,0);//修改UI,准备标定解算
			double RMS=helper.Calibration(helper.CreateobjectPoints(), new Size(1024, 768));	
			publishProgress(-1,1);//修改UI,完成标定解算
			return RMS;
		}
		
		/**
		 * 异步操作后的提示操作
		 * @param result doInBackground()方法中得到的成功处理影像张数
		 * */
		@Override
		protected void onPostExecute(Double result)
		{
			if(result<1)
				imageView.setImageResource(R.drawable.right);
			
			NormalDialog dialog=new NormalDialog(context, "结果", "重投影误差为"+result);
			dialog.Create().show();
			btn_next.setEnabled(true);
		}
		
		/**
		 * 异步操作中的修改该UI的操作
		 * @param values 调用publishProgress()方法的回调函数
		 * */
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			int index=values[0]; int flag=values[1];
			if(index!=-1)
				adapter.UpdateState(index, flag);
			else
			{
				if(flag==0)
				{
					progressBar.setVisibility(View.VISIBLE);
				}
				else 
				{
					progressBar.setVisibility(View.INVISIBLE);
					imageView.setVisibility(View.VISIBLE);
				}
			}
		}
	}

}
