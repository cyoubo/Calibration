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
 * ���ڱ�ʾ�궨������Ӱ����������ȡ��adapter
 * */
public class FeatureDetectingAdapter extends BaseAdapter
{
	private Context context;
	private String[] imagepaths;
	
	/**״̬��ʾ0Ϊδ���� 1Ϊ������ 2 Ϊ����ɹ� 3Ϊ����ʧ��*/
	private int[] state;
	
	/**
	 * ״̬��ʾ0Ϊδ���� 1Ϊ������ 2 Ϊ����ɹ� 3Ϊ����ʧ��*/
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
		if(state[position]==0)//δ����״̬
		{
			progressbar.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.INVISIBLE);
		}
		else if(state[position]==1)//������
		{
			progressbar.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.INVISIBLE);
		}
		else if(state[position]==2)//����ɹ�
		{
			progressbar.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(R.drawable.right);
			
		}
		else //����ʧ��
		{
			progressbar.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(R.drawable.error);
		}
		
		return convertView;
	}
	
	/**
	 * ������item��Ϊ�����е�״̬
	 * */
	public void PreProgress()
	{
		for(int i=0;i<state.length;i++)
			state[i]=1;
		
	}
	/**
	 * ����ָ�������ͱ�ʾ���޸�item״̬<br>
	 * ״̬��ʾ0Ϊδ���� 1Ϊ������ 2 Ϊ����ɹ� 3Ϊ����ʧ��
	 * */
	public void UpdateState(int index,int state)
	{
		this.state[index]=state;
		notifyDataSetChanged();
	}
	/**
	 * ���ɸѡ�ɹ����ļ�������<br>
	 * @param Dirpath �ļ����ڵ�·�������ںϳɾ���·��
	 * @return �ϳɺ���������ļ�·������
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
