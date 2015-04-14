package com.calibration.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.calibration.beans.CalibrationResultBeans;
import com.calibration.dao.CalibrationResultDao;
import com.example.calibration.R;
import com.system.Initialization;
import com.system.IntentKey;
import com.system.SystemUtils;
import com.tool.SqliteHelper.SQLiteHelper;

/**
 * 该界面用于标定参数的展示和入库
 * */
public class ACalibrationResultDisplay extends Activity implements
		Initialization
{

	private EditText et_no;
	private TextView tv_fx,tv_fy,tv_cx,tv_cy,tv_rms,tv_k1,tv_k2,tv_p1,tv_p2;
	private Button btn_save;
	
	private CalibrationResultBeans beans;
	private CalibrationResultDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.acalibrationresultdisplay);
		Initialization();
		//获取intent中包含的标定结果
		beans=(CalibrationResultBeans)getIntent().getExtras().getSerializable(IntentKey.CalibrationResult.toString());
		dao=new CalibrationResultDao(beans);
		//展示结果
		BeansDisplay();
	}
	
	private void BeansDisplay()
	{
		tv_fx.setText(""+beans.getFx());
		tv_fy.setText(""+beans.getFy());
		tv_cx.setText(""+beans.getCx());
		tv_cy.setText(""+beans.getCy());
		tv_rms.setText(""+beans.getRms());
		tv_k1.setText(""+beans.getK1());
		tv_k2.setText(""+beans.getK2());
		tv_p1.setText(""+beans.getP1());
		tv_p2.setText(""+beans.getP2());
		et_no.setText(dao.getCalibrationNo());
	}
	
	//保存事件监听
	private OnClickListener saveClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			SQLiteHelper helper=new SQLiteHelper(new SystemUtils().getDataBaseFullPath());
			helper.OpenDataBase(false);
			if(helper.Insert(dao)==-1)
				Toast.makeText(ACalibrationResultDisplay.this, "保存失败", Toast.LENGTH_SHORT).show();
			
		}
	};
	
	@Override
	public void Initialization()
	{
		et_no=(EditText)findViewById(R.id.acalibrationresultdisplay_no);
		tv_fx=(TextView)findViewById(R.id.acalibrationresultdisplay_fx);
		tv_fy=(TextView)findViewById(R.id.acalibrationresultdisplay_fy);
		tv_cx=(TextView)findViewById(R.id.acalibrationresultdisplay_cx);
		tv_cy=(TextView)findViewById(R.id.acalibrationresultdisplay_cy);
		tv_rms=(TextView)findViewById(R.id.acalibrationresultdisplay_rms);
		tv_k1=(TextView)findViewById(R.id.acalibrationresultdisplay_k1);
		tv_k2=(TextView)findViewById(R.id.acalibrationresultdisplay_k2);
		tv_p1=(TextView)findViewById(R.id.acalibrationresultdisplay_p1);
		tv_p2=(TextView)findViewById(R.id.acalibrationresultdisplay_p2);
		btn_save=(Button)findViewById(R.id.acalibrationresultdisplay_save);
		btn_save.setOnClickListener(saveClickListener);
	}

}
