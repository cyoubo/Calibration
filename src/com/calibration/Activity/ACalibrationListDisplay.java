package com.calibration.Activity;

import java.util.List;

import android.R.anim;
import android.R.style;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.calibration.beans.CalibrationResultBeans;
import com.calibration.beanshelper.CalibrationResultBeansHelper;
import com.component.CalibrationResultAdapter;
import com.system.Initialization;
import com.system.IntentKey;
import com.system.SystemUtils;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;

public class ACalibrationListDisplay extends ListActivity implements
		Initialization
{

	private CalibrationResultAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Initialization();
		List<CalibrationResultBeans> list=CalibrationResultBeansHelper.queryAll(new SQLiteOrmSDContext(this, new SystemUtils()));
		adapter=new CalibrationResultAdapter(this, list);
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(itemClickListener);
		
	}
	
	OnItemClickListener itemClickListener=new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			CalibrationResultBeans temp=adapter.getClickedItem(position);
			Intent intent=new Intent(ACalibrationListDisplay.this, ACalibrationResultDisplay.class);
			intent.putExtra(IntentKey.CalibrationResult.toString(), temp);
			startActivity(intent);
		}
	};
	
	@Override
	public void Initialization()
	{
		this.setTheme(style.Theme_Light_NoTitleBar);
	}

}
