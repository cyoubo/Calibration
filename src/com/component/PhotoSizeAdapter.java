/**
 * 
 */
package com.component;



import com.example.calibration.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *用于完成手机相机支持的分辨率的Adapter
 */
public class PhotoSizeAdapter extends BaseAdapter
{

	private Context mContext;
	private SerializableSize[] mySize;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            上下文环境
	 * @param mySize
	 *            机相机支持的分辨率的数组
	 * */
	public PhotoSizeAdapter(Context context, SerializableSize[] mySize)
	{
		this.mContext = context;
		this.mySize = mySize;
	}

	public int getCount()
	{
		// TODO Auto-generated method stub
		return mySize.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.sizeadapteritem, null);
			TextView tv = (TextView) convertView
					.findViewById(R.id.sizeadapteritem_tv);
			SerializableSize size = mySize[position];
			tv.setText(String.format("%5d x %5d", size.height, size.width));
			tv.setTextColor(Color.BLACK);
		}
		return convertView;
	}

	public SerializableSize getCurrentSize(int position)
	{
		return mySize[position];
	}

}
