package com.calibration.Activity;

import org.opencv.core.Size;

import FileUtils.Utils.DirectoryUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.component.FeatureDetectingAdapter;
import com.example.calibration.R;
import com.opecvutils.CalibrationHelper;
import com.system.GlobleParam;
import com.system.Initialization;
import com.system.IntentKey;
import com.system.SystemUtils;
import com.tool.mydialog.NormalDialog;

/**
 * 该界面用于待解算影像的初步角点提取
 * */
public class APhotoFeatureDetect extends Activity implements Initialization
{
	private ListView listView;
	private Button btn_check,btn_next;
	
	private FeatureDetectingAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotofeaturedetect);
		Initialization();
	}
	
	//检测用事件响应
	private OnClickListener checkClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//异步执行特征点提取
			FeatureDectectAsyncTask task=new FeatureDectectAsyncTask(APhotoFeatureDetect.this);
			task.execute(GlobleParam.Create().getThumbnailImagePath());
			
		}
	};
	
	//下一步事件响应	
	private OnClickListener nextClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//获得提取成功的缩略图的路径，并更新全局变量
			GlobleParam.Create().setThumbnailImagePath(adapter.getSuccessedFiles(SystemUtils.getPictureThumbnailPath()));
			startActivity(new Intent(APhotoFeatureDetect.this, APhotoFeatureLocation.class));
		}
	};
	
	OnItemClickListener itemClickListener=new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			// TODO Auto-generated method stub
			String paths=GlobleParam.Create().getThumbnailImagePath()[position];
			Intent intent=new Intent(APhotoFeatureDetect.this, APhotoScanDisplay.class);
			intent.putExtra(IntentKey.PhotoThumbnailPath.toString(), paths);
			intent.putExtra(IntentKey.DrawHarris.toString(), true);
			startActivity(intent);
		}
	};
	
	@Override
	public void Initialization()
	{
		// TODO Auto-generated method stub
		listView=(ListView)findViewById(R.id.aphotofeaturedetect_listview_keypoint);
		btn_check=(Button)findViewById(R.id.aphotofeaturedetect_btn_check);
		btn_next=(Button)findViewById(R.id.aphotofeaturedetect_btn_next);
		btn_check.setOnClickListener(checkClickListener);
		btn_next.setOnClickListener(nextClickListener);
		
		String[] paths=DirectoryUtils.SpiltFullPathToNames(GlobleParam.Create().getThumbnailImagePath());
		adapter=new FeatureDetectingAdapter(this, paths);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);

	}
	
	/**
	 * 内部类：<br>
	 * 完成异步的特征点提取的异步任务类型
	 * */
	public class FeatureDectectAsyncTask extends AsyncTask<String, Integer, Integer>
	{
		/**上下文环境*/
		private Context context;
		/**
		 * 构造函数
		 * @author Administrator
		 * @param context 上下文环境
		 * */
		private FeatureDectectAsyncTask(Context context)
		{
			this.context=context;
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
		protected Integer doInBackground(String... params)
		{
			int count=0;//计数器
			CalibrationHelper helper=new CalibrationHelper(new Size(9, 6));
			adapter.PreProgress();//开启处理
			
			for(int i=0;i<params.length;i++)
			{
				//若提取成功则返回2 标示处理成功，否则返回3 表示处理失败
				int flag=helper.FeatureDetectbyThumbnail(params[i])?2:3;
				if(flag==2) //处理成功，计数器加1
					count++;
				publishProgress(i,flag);//修改UI
			}
			return count;
		}
		
		/**
		 * 异步操作后的提示操作
		 * @param result doInBackground()方法中得到的成功处理影像张数
		 * */
		@Override
		protected void onPostExecute(Integer result)
		{
			if(result<3)
			{
				NormalDialog dialog=new NormalDialog(context, "警告", "待解算影像过少，请重新选择影像");
				dialog.Create().show();
			}
			else
			{
				btn_next.setEnabled(true);
			}
		}
		
		/**
		 * 异步操作中的修改该UI的操作
		 * @param values 调用publishProgress()方法的回调函数
		 * */
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			int index=values[0]; int flag=values[1];
			adapter.UpdateState(index, flag);
		}
	}
	

}
