package com.calibration.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.calibration.R;
import com.system.Initialization;
import com.system.SystemUtils;
import com.tool.SqliteHelperOrm.SQLiteOrmHelper;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;

public class MainActivity extends Activity implements Initialization
{

	private TextView tv_takephote,tv_calibration,tv_scan;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		Initialization();
	}
	@Override
	public void Initialization()
	{
		// TODO Auto-generated method stub
		this.tv_takephote=(TextView)findViewById(R.id.activity_main_tv_takingphoto);
		this.tv_scan=(TextView)findViewById(R.id.activity_main_tv_scan);
		this.tv_calibration=(TextView)findViewById(R.id.activity_main_tv_calibration);
		
		tv_calibration.setOnClickListener(clickListener);
		tv_scan.setOnClickListener(clickListener);
		tv_takephote.setOnClickListener(clickListener);
	}
	
	public OnClickListener clickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.activity_main_tv_calibration:
				{
					
				}break;
				case R.id.activity_main_tv_scan:
				{
					startActivity(new Intent(MainActivity.this, APhotoPicking.class));
				}break;
				case R.id.activity_main_tv_takingphoto:
				{
					startActivity(new Intent(MainActivity.this, APhontoTaking.class));
				}break;
				default:
					break;
			}
			
		}
	};
	
	

}
