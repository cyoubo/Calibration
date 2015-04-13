/**
 * 
 */
package com.calibration.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.component.PhotoSizeAdapter;
import com.component.TakingParam;
import com.example.calibration.R;
import com.system.IntentKey;
import com.tool.mydialog.ListDialog;

/**
 * 该Activity用于完成拍摄参数设置的界面处理
 */
public class APhotoParamSetting extends Activity
{
	private CheckBox checkBox;
	private Button button_sure, button_choose;
	private TextView tv_size;

	private TakingParam dao;
	private PhotoSizeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aphotoparamsetting);
		Log.i("demo", " demo into setting");
		dao = (TakingParam) getIntent().getExtras().getSerializable(
				IntentKey.PhotoParam.toString());
		Log.i("demo", "demo get dao");
		Log.i("demo", "demo dao is null " + (dao == null));
		initialization();

	}

	private void initialization()
	{

		checkBox = (CheckBox) findViewById(R.id.aphotoparamsetting_cb_flashmode);
		button_sure = (Button) findViewById(R.id.aphotoparamsetting_btn_sure);
		button_choose = (Button) findViewById(R.id.aphotoparamsetting_btn_choosesize);
		tv_size = (TextView) findViewById(R.id.aphotoparamsetting_tv_size);

		adapter = new PhotoSizeAdapter(APhotoParamSetting.this,
				dao.getMysizes());
		checkBox.setOnCheckedChangeListener(checkedchagelistener);

		button_sure.setOnClickListener(btn_sure_listener);
		button_choose.setOnClickListener(btn_size_listener);

		tv_size.setText(dao.CurrentSize_String());

		this.setTitle("拍照选项");
		this.setTitleColor(Color.parseColor("#4F94CD"));

	}

	private OnCheckedChangeListener checkedchagelistener = new OnCheckedChangeListener()
	{

		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked)
		{
			dao.setIsFlashed(isChecked);
		}
	};

	private OnClickListener btn_sure_listener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(APhotoParamSetting.this,
					APhontoTaking.class);
			intent.putExtra(IntentKey.PhotoParam.toString(), dao);
			setResult(1, intent);
			finish();
		}
	};

	private OnClickListener btn_size_listener = new OnClickListener()
	{
		public void onClick(View v)
		{
			ListDialog listDialog = new ListDialog(APhotoParamSetting.this,
					"请选择");
			listDialog.SetAdapter(adapter,
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							dao.setCurrentSize(adapter.getCurrentSize(which));
							tv_size.setText(dao.CurrentSize_String());
						}
					});
			listDialog.Create().show();
		}
	};
}
