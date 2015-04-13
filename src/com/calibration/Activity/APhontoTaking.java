package com.calibration.Activity;

import java.util.List;

import FileUtils.Utils.DirectoryUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.component.TakingParam;
import com.example.calibration.R;
import com.system.IntentKey;
import com.system.SystemUtils;
import com.tool.BitmapHelper;
//真机用代码
//测试用代码

/** 该Activity用于完成拍摄工作的界面处理 */
public class APhontoTaking extends Activity
{
	// 定义UI控件
	private SurfaceView surface;
	private ImageButton ibt_more, ibt_scan;
	private Button btn_take;
	// 定义控制器
	private SurfaceHolder holder;
	private Camera camera;
	// 定义模型类
	private TakingParam paramdao;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aphototaking);

		initialization();
	}

	/* 用于完成UI空间的绑定与事件监听的设置 */
	private void initialization()
	{
		// 控件绑定
		 surface = (SurfaceView) findViewById(R.id.MainActivity_surfaceView);
		 btn_take = (Button) findViewById(R.id.MainActivity_btn_take);
		 ibt_more = (ImageButton) findViewById(R.id.MainActivity_ibt_more);
		 ibt_scan = (ImageButton) findViewById(R.id.MainActivity_ibt_scan);
		 //设置监听
		 btn_take.setOnClickListener(takeListener);
		 ibt_more.setOnClickListener(moreListener);
		 ibt_scan.setOnClickListener(scanListener);
		// 环境检验
		DirectoryUtils.CreateDirectory(SystemUtils.getPicturePath());
		DirectoryUtils.CreateDirectory(SystemUtils.getPictureThumbnailPath());
		// 数据类生成
		paramdao = new TakingParam();
	}

	// 相片拍摄的动作的回调器
	private PictureCallback takepiccallback = new PictureCallback()
	{

		public void onPictureTaken(byte[] data, Camera camera)
		{
			BitmapHelper helper = new BitmapHelper(data);
			String name = SystemUtils.getSystemDateString();
			int result = helper.SaveintoFilewithThumbnail(
					SystemUtils.getPicturePath(),
					SystemUtils.getPictureThumbnailPath(), name,
					CompressFormat.JPEG);

			camera.startPreview();

			String message = result == 0 ? "保存成功" : "保存失败";
			Toast.makeText(APhontoTaking.this, message, Toast.LENGTH_SHORT).show();
		}
	};
	/* 用于拍照事件的回调 */
	private SurfaceHolder.Callback Holdercallback = new SurfaceHolder.Callback()
	{
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height)
		{

		}

		public void surfaceCreated(SurfaceHolder holder)
		{
			camera = Camera.open();
			try
			{
				camera.setPreviewDisplay(APhontoTaking.this.holder);
				camera.setDisplayOrientation(90);
				Parameters parameters = camera.getParameters();

				parameters.setPictureFormat(ImageFormat.JPEG);
				List<String> terList = parameters.getSupportedFocusModes();
				for (String str : terList)
					Log.i("demo", str);
				// 红米手机测试版
				parameters.setFocusMode("manual");
				// 普通手机版
				// parameters.setFocusMode(parameters.FOCUS_MODE_FIXED);

				if (paramdao.isIsFlashed())
					parameters.setFlashMode(Parameters.FLASH_MODE_ON);
				else
					parameters.setFlashMode(Parameters.FLASH_MODE_OFF);

				paramdao.setmList(parameters.getSupportedPictureSizes());
				parameters.setPictureSize(paramdao.getCurrentSize().width,
						paramdao.getCurrentSize().height);
				camera.setParameters(parameters);
			}
			catch (Exception e)
			{
				Log.e("demo", "exception " + e.getMessage());
				camera.release();
			}
			camera.startPreview();

		}

		public void surfaceDestroyed(SurfaceHolder holder)
		{
			camera.stopPreview();
			holder.removeCallback(Holdercallback);
			camera.release();
		}
	};

	private OnClickListener scanListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			startActivity(new Intent(APhontoTaking.this, APhotoScan.class));
		}
	};

	private OnClickListener moreListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			Intent intent = new Intent(APhontoTaking.this,
					APhotoParamSetting.class);
			intent.putExtra(IntentKey.PhotoParam.toString(), paramdao);
			startActivityForResult(intent, 1);

		}
	};
	/* 单击拍摄按钮所执行的事件响应 */
	private OnClickListener takeListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			camera.takePicture(null, null, takepiccallback);
		}
	};

	protected void onResume()
	{
		super.onResume();

		holder = surface.getHolder();
		holder.addCallback(Holdercallback);
	};

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1 && requestCode == 1)
		{
			paramdao = (TakingParam) data
					.getSerializableExtra(IntentKey.PhotoParam.toString());

			Parameters parameters = camera.getParameters();

			if (paramdao.isIsFlashed())
				parameters.setFlashMode(Parameters.FLASH_MODE_ON);
			else
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
			parameters.setPictureSize(paramdao.getCurrentSize().width,
					paramdao.getCurrentSize().height);
			camera.setParameters(parameters);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			finish();
		}
		return true;
	}
}