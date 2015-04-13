package com.calibration.dao;

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
	
	

	

}
