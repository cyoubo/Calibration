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
 * �������Ӱ����ز���
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
	 * ȷ����ť���¼������������첽�ز�������
	 * */
	OnClickListener clickListener=new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			//�����첽����
			RemapAsynTask remapAsynTask=new RemapAsynTask(GlobleParam.Create().getRemapImagePath());
			//ִ���첽����
			remapAsynTask.execute(beansHelper);
		}
	};
	
	OnClickListener imageClickListener=new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			ListDialog dialog=new ListDialog(APhotoRemap.this, "�궨�ɹ�");
			dialog.SetAdapter(adapter, new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					//��ȡѡ���ı궨���
					beansHelper =new CalibrationResultHelper(adapter.getClickedItem(which));
					//��ʾ�궨�������
					tv_paramindex.setText(beansHelper.getCalibrationLongName());
				}
			});
			dialog.Create().show();
			
		}
	};
	
	//����ͼƬ�ֱ��ʵ�TextView
	private void DisplayImageresolution()
	{
		BitmapHelper helper=new BitmapHelper(GlobleParam.Create().getRemapImagePath());
		int[] size=helper.getImageSize();
		width=size[0];height=size[1];	
		tv_resolution.setText(String.format("%d*%d",width,height));
	}
	//׼���궨���Adapter
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
	 * �ڲ��ࣺ�������У��ͼ���ز������첽������
	 * **/
	private class RemapAsynTask extends AsyncTask<CalibrationResultHelper, String, Integer>
	{
		/**���ز�����ͼ��·��*/
		private String imagepath;
		
		/**
		 * ���캯��<br>
		 * @param path ���ز�����ͼ��·��
		 * */
		public RemapAsynTask(String path)
		{
			this.imagepath=path;
		}
		
		@Override
		protected void onPreExecute()
		{
			tv_progress.setText("������");
			progressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Integer doInBackground(CalibrationResultHelper... params)
		{
			//����BitmapHelper_CV���������ز���
			BitmapHelper_CV cvhelper=new BitmapHelper_CV(imagepath);
			//�����ز���ͼ�����
			Mat resultmat=cvhelper.getUndistoredMat(params[0].getCameraMatrix(), params[0].getDistMatrix());
			//����BitmapHelper������ɴ洢����
			BitmapHelper helper=new BitmapHelper(BitmapHelper_CV.MatToBitmap(resultmat));
			//�ļ��洢
			String imagepath=SystemUtils.getPictureRemapPath();
			String thumbnailpath=SystemUtils.getPictureRemapThumbnailPath();
			String name=params[0].getCalibrationShortName();
			helper.SaveintoFilewithThumbnail(imagepath,thumbnailpath,name, CompressFormat.JPEG);
			//�첽����չʾУ��ͼ������ͼ
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
			tv_progress.setText("�������");
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

}
