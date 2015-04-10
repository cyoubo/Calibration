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
 * �ý������ڴ�����Ӱ��ĳ����ǵ���ȡ
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
	
	//������¼���Ӧ
	private OnClickListener checkClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//�첽ִ����������ȡ
			FeatureDectectAsyncTask task=new FeatureDectectAsyncTask(APhotoFeatureDetect.this);
			task.execute(GlobleParam.Create().getThumbnailImagePath());
			
		}
	};
	
	//��һ���¼���Ӧ	
	private OnClickListener nextClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//�����ȡ�ɹ�������ͼ��·����������ȫ�ֱ���
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
	 * �ڲ��ࣺ<br>
	 * ����첽����������ȡ���첽��������
	 * */
	public class FeatureDectectAsyncTask extends AsyncTask<String, Integer, Integer>
	{
		/**�����Ļ���*/
		private Context context;
		/**
		 * ���캯��
		 * @author Administrator
		 * @param context �����Ļ���
		 * */
		private FeatureDectectAsyncTask(Context context)
		{
			this.context=context;
		}
		
		/**
		 * �첽����ǰ����ʾ����
		 * */
		@Override
		protected void onPreExecute()
		{
			Toast.makeText(context, "��ʼ����", Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * �첽ִ�еĲ���
		 * @author Administrator
		 * @param params �����������Ӱ���·������
		 * @return ��ȡ������Ҫ���Ӱ������
		 * */
		@Override
		protected Integer doInBackground(String... params)
		{
			int count=0;//������
			CalibrationHelper helper=new CalibrationHelper(new Size(9, 6));
			adapter.PreProgress();//��������
			
			for(int i=0;i<params.length;i++)
			{
				//����ȡ�ɹ��򷵻�2 ��ʾ����ɹ������򷵻�3 ��ʾ����ʧ��
				int flag=helper.FeatureDetectbyThumbnail(params[i])?2:3;
				if(flag==2) //����ɹ�����������1
					count++;
				publishProgress(i,flag);//�޸�UI
			}
			return count;
		}
		
		/**
		 * �첽���������ʾ����
		 * @param result doInBackground()�����еõ��ĳɹ�����Ӱ������
		 * */
		@Override
		protected void onPostExecute(Integer result)
		{
			if(result<3)
			{
				NormalDialog dialog=new NormalDialog(context, "����", "������Ӱ����٣�������ѡ��Ӱ��");
				dialog.Create().show();
			}
			else
			{
				btn_next.setEnabled(true);
			}
		}
		
		/**
		 * �첽�����е��޸ĸ�UI�Ĳ���
		 * @param values ����publishProgress()�����Ļص�����
		 * */
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			int index=values[0]; int flag=values[1];
			adapter.UpdateState(index, flag);
		}
	}
	

}
