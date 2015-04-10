package com.component;

import java.util.ArrayList;
import java.util.List;

import FileUtils.Utils.DirectoryUtils;
import android.R.bool;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calibration.R;
import com.system.SystemUtils;
import com.tool.BitmapHelper;

/**
 * 该Adapter用于完成待选影像的挑选
 * **/
public class PhotosPickingAdapter extends PhotoThumbAdapter
{
	private boolean[] mIsPicked;

	public  PhotosPickingAdapter(Context context,String[] imagePaths)
	{
		super(imagePaths, DirectoryUtils.SpiltFullPathToNames(imagePaths.clone()), context);
		windowssize = SystemUtils.getWindowSize(context);
		mIsPicked=new boolean[super.imageNames.length];
	}
	
	public void UpdatePicked(int location)
	{
		this.mIsPicked[location]=(!this.mIsPicked[location]);
		this.notifyDataSetChanged();
	}
	
	public void ResetPicked()
	{
		for(int i=0;i<mIsPicked.length;i++)
			mIsPicked[i]=false;
		this.notifyDataSetChanged();
	}
	
	public String[] getPickedImagePath()
	{
		List<String> result=new ArrayList<>();
		for(int i=0;i<super.ImageThumbPath.length;i++)
		{
			if(mIsPicked[i])
				result.add(ImageThumbPath[i]);
		}
		String[] temp = new String[result.size()];
		result.toArray(temp);
		return temp;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		//if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.tool_photothumbnailitem, null);
			TextView tv_name = (TextView) convertView
					.findViewById(R.id.tool_photothumbnailitem_textView);
			ImageView ig_image = (ImageView) convertView
					.findViewById(R.id.tool_photothumbnailitem_imageView);
			tv_name.setText(this.imageNames[position]);
			if(mIsPicked[position])
				tv_name.setTextColor(Color.RED);
			BitmapHelper helper = new BitmapHelper(ImageThumbPath[position]);
			ig_image.setImageBitmap(ModifyBitmapSize(helper.getBitmap()));
		}
		return convertView;
	}
}
