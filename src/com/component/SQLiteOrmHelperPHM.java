package com.component;

import android.content.Context;
import android.util.Log;

import com.calibration.beans.CalibrationResultBeans;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tool.SqliteHelperOrm.SQLiteOrmHelper;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;

public class SQLiteOrmHelperPHM extends SQLiteOrmHelper
{
	public SQLiteOrmHelperPHM(SQLiteOrmSDContext context)
	{
		super(context, context.getInfo().getDataBaseName());
	}
	
	/**��ü򵥲�����CalibrationResultBeans��Dao����*/
	public Dao<CalibrationResultBeans, Integer> getCalibrationDao()
	{
		Dao<CalibrationResultBeans, Integer> resultDao=null;
		try
		{
			resultDao=getDao(CalibrationResultBeans.class);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			Log.e("demo", "getCalibrationDao"+e.getMessage());
		}
		return resultDao;
	}
	/**��ø��Ӳ�����CalibrationResultBeans��Dao����*/
	public RuntimeExceptionDao<CalibrationResultBeans, Integer> getCalibrationRuntimeExceptionDao()
	{
		return getRuntimeExceptionDao(CalibrationResultBeans.class);
	}

}
