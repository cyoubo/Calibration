package com.calibration.activity;

import java.util.List;

import android.R.style;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.calibration.beans.CalibrationResultBeans;
import com.calibration.beanshelper.CalibrationResultHelper;
import com.component.CalibrationResultAdapter;
import com.system.GlobleParam;
import com.system.Initialization;
import com.system.IntentKey;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;

/**
 * ʵ�ֱ궨����б�չʾ��activity
 * **/
public class ACalibrationListDisplay extends ListActivity implements Initialization
{
	private CalibrationResultAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//����ʵ��������ѯ���еı궨���
		List<CalibrationResultBeans> list=CalibrationResultHelper.queryAll(new SQLiteOrmSDContext(this, GlobleParam.Create()));
		adapter=new CalibrationResultAdapter(this, list);
		
		Initialization();
	}
	
	OnItemClickListener itemClickListener=new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			//��ת��ACalibrationResultDisplay���棬չʾ����ѡ�����ϸ�궨��Ϣ
			CalibrationResultBeans temp=adapter.getClickedItem(position);
			Intent intent=new Intent(ACalibrationListDisplay.this, ACalibrationResultDisplay.class);
			intent.putExtra(IntentKey.CalibrationResult.toString(), temp);
			startActivity(intent);
		}
	};
	
	@Override
	public void Initialization()
	{
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(itemClickListener);
		this.setTheme(style.Theme_Light_NoTitleBar);
	}

}
