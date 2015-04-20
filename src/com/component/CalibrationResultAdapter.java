package com.component;

import java.util.List;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calibration.beans.CalibrationResultBeans;
import com.example.calibration.R;

public class CalibrationResultAdapter extends BaseAdapter
{
	
	private Context context;
	private List<CalibrationResultBeans> list;
	
	public CalibrationResultAdapter(Context context,List<CalibrationResultBeans> list)
	{
		this.context=context;
		this.list=list;
	}
	
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView!=null)
		{
			LayoutInflater inflater=LayoutInflater.from(context);
			convertView=inflater.inflate(R.layout.calibrationresultitem, null);
			TextView tv_resolution=(TextView)convertView.findViewById(R.id.calibrationresultitem_tv_resolution);
			TextView tv_rms=(TextView)convertView.findViewById(R.id.calibrationresultitem_tv_rms);
			
			tv_resolution.setText(list.get(position).getResolution());
			tv_rms.setText(""+list.get(position).getRms());
		}
		
		return convertView;
	}
	
	public String getClickedItemResolution(int position)
	{
		return list.get(position).getResolution();
	}
	
	public CalibrationResultBeans getClickedItem(int position)
	{
		return list.get(position);
	}
}
