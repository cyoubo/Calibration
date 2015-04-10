package com.component;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.calibration.R;

/**
 * 用于标示标定待解算影像特征点提取的adapter
 * */
public class FeatureDetectingAdapter extends BaseAdapter
{
	private Context context;
	private String[] imagepaths;
	
	/**状态标示0为未处理 1为处理中 2 为处理成功 3为处理失败*/
	private int[] state;
	
	/**
	 * 状态标示0为未处理 1为处理中 2 为处理成功 3为处理失败*/
	public FeatureDetectingAdapter(Context context,String[] imagepaths)
	{
		this.context=context;
		this.imagepaths=imagepaths;
		state=new int[this.imagepaths.length];
		for(int i=0;i<state.length;i++)
			state[i]=0;
	}
	
	
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return imagepaths.length;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return imagepaths[position];
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
		LayoutInflater inflater=LayoutInflater.from(context);
		convertView=inflater.inflate(R.layout.featuredetectingadapteritem, null);
		TextView tv_title=(TextView)convertView.findViewById(R.id.featuredetectingdapteritem_title);
		ProgressBar progressbar=(ProgressBar)convertView.findViewById(R.id.featuredetectingdapteritem_progressBar);
		ImageView imageView=(ImageView)convertView.findViewById(R.id.featuredetectingdapteritem_imageView);
		
		tv_title.setText(imagepaths[position]);
		if(state[position]==0)//未处理状态
		{
			progressbar.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.INVISIBLE);
		}
		else if(state[position]==1)//处理中
		{
			progressbar.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.INVISIBLE);
		}
		else if(state[position]==2)//处理成功
		{
			progressbar.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(R.drawable.right);
			
		}
		else //处理失败
		{
			progressbar.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(R.drawable.error);
		}
		
		return convertView;
	}
	
	/**
	 * 将所有item置为处理中的状态
	 * */
	public void PreProgress()
	{
		for(int i=0;i<state.length;i++)
			state[i]=1;
		
	}
	/**
	 * 根据指定索引和标示符修改item状态<br>
	 * 状态标示0为未处理 1为处理中 2 为处理成功 3为处理失败
	 * */
	public void UpdateState(int index,int state)
	{
		this.state[index]=state;
		notifyDataSetChanged();
	}
	/**
	 * 获得筛选成功的文件名数组<br>
	 * @param Dirpath 文件所在的路径，用于合成绝对路径
	 * @return 合成后的完整的文件路径数组
	 * */
	public String[] getSuccessedFiles(String Dirpath)
	{
		List<String> result=new ArrayList<>();
		for(int i=0;i<this.state.length;i++)
		{
			if(this.state[i]==2)
				result.add(Dirpath+"/"+this.imagepaths[i]);
		}
		String[] temp=new String[result.size()];
		result.toArray(temp);
		return temp;
	}
	
	

}
