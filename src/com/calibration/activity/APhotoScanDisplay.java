/**
 * 
 */
package com.calibration.activity;

import org.opencv.core.Size;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.calibration.R;
import com.opecvutils.BitmapHelper_CV;
import com.opecvutils.CalibrationHelper;
import com.system.IntentKey;
import com.system.SystemUtils;

/**
 * @author Administrator 用于完成照片详情展示的Activity
 */
public class APhotoScanDisplay extends Activity
{
	private ImageView ig;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotoscandisplay);
		initialization();
		
		//利用opencv组件由文件路径生成位图
		String temp = getIntent().getExtras().getString(
				IntentKey.PhotoThumbnailPath.toString());
		temp = SystemUtils.ConvetThumbnailPathToImage(temp);
		BitmapHelper_CV helper_CV=new BitmapHelper_CV(temp);
		ig.setImageBitmap(helper_CV.ConvertToBitMap());
		
		//若intent含有DrawHarris，则绘制角点连线图
		if(getIntent().getExtras().containsKey(IntentKey.DrawHarris.toString()))
		{
			CalibrationHelper helper=new CalibrationHelper(new Size(6, 9));
			ig.setImageBitmap(helper.DrawHarris(temp));
		}
	}
	private void initialization()
	{
		ig = (ImageView) findViewById(R.id.aphotoscaninfo_imageView);
	}
	
	
}
