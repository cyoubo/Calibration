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
//����ô���
//�����ô���

/** ��Activity����������㹤���Ľ��洦�� */
public class APhontoTaking extends Activity
{
	// ����UI�ؼ�
	private SurfaceView surface;
	private ImageButton ibt_more, ibt_scan;
	private Button btn_take;
	// ���������
	private SurfaceHolder holder;
	private Camera camera;
	// ����ģ����
	private TakingParam paramdao;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aphototaking);

		initialization();
	}

	/* �������UI�ռ�İ����¼����������� */
	private void initialization()
	{
		// �ؼ���
		 surface = (SurfaceView) findViewById(R.id.MainActivity_surfaceView);
		 btn_take = (Button) findViewById(R.id.MainActivity_btn_take);
		 ibt_more = (ImageButton) findViewById(R.id.MainActivity_ibt_more);
		 ibt_scan = (ImageButton) findViewById(R.id.MainActivity_ibt_scan);
		 //���ü���
		 btn_take.setOnClickListener(takeListener);
		 ibt_more.setOnClickListener(moreListener);
		 ibt_scan.setOnClickListener(scanListener);
		// ��������
		DirectoryUtils.CreateDirectory(SystemUtils.getPicturePath());
		DirectoryUtils.CreateDirectory(SystemUtils.getPictureThumbnailPath());
		// ����������
		paramdao = new TakingParam();
	}

	// ��Ƭ����Ķ����Ļص���
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

			String message = result == 0 ? "����ɹ�" : "����ʧ��";
			Toast.makeText(APhontoTaking.this, message, Toast.LENGTH_SHORT).show();
		}
	};
	/* ���������¼��Ļص� */
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
				// �����ֻ����԰�
				parameters.setFocusMode("manual");
				// ��ͨ�ֻ���
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
	/* �������㰴ť��ִ�е��¼���Ӧ */
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