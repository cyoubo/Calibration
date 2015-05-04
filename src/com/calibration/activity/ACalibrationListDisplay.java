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
 * 实现标定结果列表展示的activity
 * **/
public class ACalibrationListDisplay extends ListActivity implements Initialization
{
	private CalibrationResultAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//利用实体帮助类查询所有的标定结果
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
			//跳转至ACalibrationResultDisplay界面，展示单击选项的详细标定信息
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
