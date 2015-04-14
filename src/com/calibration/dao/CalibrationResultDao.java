package com.calibration.dao;

import org.opencv.core.Mat;

import com.calibration.beans.CalibrationResultBeans;
import com.tool.SqliteHelper.AbstratctDataDao;
import com.tool.SqliteHelper.ISQLiteDataInterface.ISQLiteIndexDataDAO;

public class CalibrationResultDao extends AbstratctDataDao<CalibrationResultBeans> 
implements ISQLiteIndexDataDAO
{
	private CalibrationResultBeans beans;
	
	public CalibrationResultDao(CalibrationResultBeans beans)
	{
		this.beans=beans;
	}

	@Override
	public String getTableName()
	{
		return "CalibrationResult";
	}

	@Override
	public String[] getPrimaryKeys()
	{
		return new String[]{"index"};
	}

	@Override
	public String[] getPrimaryValues()
	{
		return new String[]{""+beans.getIndex()};
	}

	@Override
	public CalibrationResultBeans getBeansObj()
	{
		return beans;
	}

	@Override
	protected Class<CalibrationResultBeans> getBeansClass()
	{
		return CalibrationResultBeans.class;
	}

	@Override
	public String getIndexColumnName()
	{
		return  "index";
	}

	@Override
	public String getIndexValues()
	{
		// TODO Auto-generated method stub
		return  ""+beans.getIndex();
	}

	@Override
	public void setBeansObj(CalibrationResultBeans t)
	{
		// TODO Auto-generated method stub
		this.beans=t;
	}
	
	public void setRms(double rms)
	{
		this.beans.setRms(rms);
	}
	
	public double getRms()
	{
		return this.beans.getRms();
	}
	
	
	public void setCammerMatrix(Mat mat)
	{
		this.beans.setFx(mat.get(0, 0)[0]);
		this.beans.setFy(mat.get(1, 1)[0]);
		this.beans.setCx(mat.get(0, 2)[0]);
		this.beans.setCy(mat.get(1, 2)[0]);
	}
	
	public void setDistMatrix(Mat mat)
	{
		this.beans.setK1(mat.get(0,0)[0]);
		this.beans.setK2(mat.get(1,0)[0]);
		this.beans.setP1(mat.get(3,0)[0]);
		this.beans.setP2(mat.get(4,0)[0]);
	}
	
	public void setResolution(int width,int height)
	{
		this.beans.setResolution(""+width+"*"+height);
	}
	
	public void setResultdate(String date)
	{
		this.beans.setResultdate(date);
	}
	
	public  String getCalibrationNo()
	{
		return beans.getResolution()+"||"+beans.getResultdate();
	}

	

}
