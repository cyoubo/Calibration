package com.component;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calibration.beans.CalibrationResultBeans;
import com.example.calibration.R;

/**
 * չʾ�˶��Y����Adapter
 * */
public class CalibrationResultAdapter extends BaseAdapter
{

	
	private Context context;
	private List<CalibrationResultBeans> list;

	/**
	 * ���캯��<br>
	 * @param context �����ĭh��
	 * @param list �˶��Y���б�
	 * */
	public CalibrationResultAdapter(Context context,List<CalibrationResultBeans> list)
	{
		this.context=context;
		this.list=list;
		Log.d("demo", "list size is "+list.size());

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
		if(convertView==null)
		{
			LayoutInflater inflater=LayoutInflater.from(context);
			convertView=inflater.inflate(R.layout.calibrationresultitem, null);
			TextView tv_resolution=(TextView)convertView.findViewById(R.id.calibrationresultitem_tv_resolution);
			TextView tv_rms=(TextView)convertView.findViewById(R.id.calibrationresultitem_tv_rms);
			TextView tv_index=(TextView)convertView.findViewById(R.id.calibrationresultitem_tv_index);
	
			tv_resolution.setText(list.get(position).getResolution());
			tv_rms.setText(""+list.get(position).getRms());
			tv_index.setText(""+list.get(position).getIndex());
		}

		return convertView;
	}

	/**
	 * �@ȡָ��index��beans�ķֱ���
	 **/
	public String getClickedItemResolution(int position)
	{
		return list.get(position).getResolution();
	}
	/**
	 * �@ȡָ��index��beans����
	 **/
	public CalibrationResultBeans getClickedItem(int position)
	{
		return list.get(position);
	}
}
