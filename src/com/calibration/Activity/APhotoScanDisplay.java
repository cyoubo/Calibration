/**
 * 
 */
package com.calibration.Activity;

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
		String temp = getIntent().getExtras().getString(
				IntentKey.PhotoThumbnailPath.toString());
		temp = SystemUtils.ConvetThumbnailPathToImage(temp);
		BitmapHelper_CV helper_CV=new BitmapHelper_CV(temp);
		ig.setImageBitmap(helper_CV.ConvertToBitMap());
		
		if(getIntent().getExtras().containsKey(IntentKey.DrawHarris.toString()))
		{
			CalibrationHelper helper=new CalibrationHelper(new Size(6, 9));
			ig.setImageBitmap(helper.DrawHarris(temp));
		}
		
		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	private void initialization()
	{
		ig = (ImageView) findViewById(R.id.aphotoscaninfo_imageView);
	}
	
	
}
