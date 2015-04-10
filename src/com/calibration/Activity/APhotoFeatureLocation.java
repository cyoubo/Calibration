package com.calibration.Activity;

import java.util.List;

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
import android.widget.ListView;
import android.widget.Toast;

import com.component.FeatureDetectingAdapter;
import com.example.calibration.R;
import com.opecvutils.CalibrationHelper;
import com.system.GlobleParam;
import com.system.Initialization;
import com.tool.mydialog.NormalDialog;
/**
 * �ý������ڴ�����Ӱ��Ľǵ������ؼ����궨λ
 * */
public class APhotoFeatureLocation extends Activity implements Initialization
{
	private ListView listView;
	private Button btn_check,btn_next;
	
	private FeatureDetectingAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotofeaturelocation);
		Initialization();
	}
	
	//������¼���Ӧ
	private OnClickListener checkClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			//�첽ִ����������ȡ
			FeatureLocationAsyncTask task=new FeatureLocationAsyncTask(APhotoFeatureLocation.this);
			task.execute(GlobleParam.Create().getThumbnailImagePath());
			
		}
	};
	
	//��һ���¼���Ӧ	
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

		String[] paths=DirectoryUtils.SpiltFullPathToNames(GlobleParam.Create().getThumbnailImagePath());
		adapter=new FeatureDetectingAdapter(this, paths);
		listView.setAdapter(adapter);
		
	}
	
	/**
	 * �ڲ��ࣺ<br>
	 * ����첽����������ȡ���첽��������
	 * */
	public class FeatureLocationAsyncTask extends AsyncTask<String, Integer, Double>
	{
		/**�����Ļ���*/
		private Context context;
		/**Ӱ��ߴ�*/
		private Size size;
		
		/**
		 * ���캯��
		 * @author Administrator
		 * @param context �����Ļ���
		 * */
		private FeatureLocationAsyncTask(Context context)
		{
			this.context=context;
		}
		/**
		 * ��ȡӰ��ߴ�
		 * */
		public Size getSize()
		{
			return size;
		}
		/**
		 * ����Ӱ��ߴ�
		 * */
		public void setSize(Size size)
		{
			this.size = size;
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
		 * @param params ������������Ӱ���·������
		 * @return ��ȡ������Ҫ���Ӱ������
		 * */
		@Override
		protected Double doInBackground(String... params)
		{
			//�����˶�����
			CalibrationHelper helper=new CalibrationHelper(new Size(9, 6));
			helper.setImagePaths(GlobleParam.Create().getThumbnailImagePath());
			//�@ȡӰ��ā����ؼ����c����
			adapter.PreProgress();//��������
			for(int i=0;i<params.length;i++)
			{
				//����ȡ�ɹ��򷵻�2 ��ʾ�����ɹ������򷵻�3 ��ʾ����ʧ��
				int flag=helper.FeatureLocated(i)?2:3;
				publishProgress(i,flag);//�޸�UI
			}
			//�˶�̎��
			//helper.Calibration(helper.CreateobjectPoints(), new Size(1024, 768));
			
			return 4.0;
		}
		
		/**
		 * �첽���������ʾ����
		 * @param result doInBackground()�����еõ��ĳɹ�����Ӱ������
		 * */
		@Override
		protected void onPostExecute(Double result)
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