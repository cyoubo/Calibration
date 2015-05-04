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

/*** ��Activity�������Ӱ���GridVIew������洦�� <br>
 * �������
 * <ol>
 * <li>�����в鿴������Ӱ�񣬼�����intent��IsremapΪfalse</li>
 * <li>ѡ��鿴�ػ�ͼ�񣬼�����intent��IsremapΪtrue</li>
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
		//���չʾ��ʶ������ȷ������Դ
		flag_isremap=getIntent().getExtras().getBoolean(IntentKey.Isremap.toString());
		
		initialization();
	}

	private void initialization()
	{
		// �󶨿ؼ�
		grid = (GridView) findViewById(R.id.aphotoscan_gridview);
		
		String[] paths=null;
		if(flag_isremap)
		{
			//��Ϊ�ػ����ȡ�ػ�����ͼ�ļ�·��
			paths = DirectoryUtils.GetSubFiles(
				SystemUtils.getPictureRemapThumbnailPath(), new JPGEFillter(), true);
		}
		else
		{
			//����Ϊ�ػ����ȡӰ������ͼ�ļ�·��
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
			// ������ͼ·�����ݸ�APhotoScanInfo
			Intent intent = new Intent(APhotoScan.this, APhotoScanDisplay.class);
			intent.putExtra(IntentKey.PhotoThumbnailPath.toString(),
					adapter.getPhotoThumbnailPath(arg2));
			startActivity(intent);
		}
	};
}
