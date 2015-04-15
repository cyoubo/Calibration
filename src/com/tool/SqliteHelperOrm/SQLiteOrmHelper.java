package com.tool.SqliteHelperOrm;

import com.calibration.beans.CalibrationResultBeans;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.opecvutils.CalibrationHelper;

import android.R.bool;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class SQLiteOrmHelper extends OrmLiteSqliteOpenHelper
{

	public SQLiteOrmHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion)
	{
		super(new SQLiteOrmSDContext(context), databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}
	
	public SQLiteOrmHelper(Context context, String databaseName)
	{
		super(new SQLiteOrmSDContext(context), databaseName, null, 1);
	}
	
	/**
	 * 在IDataBaseInfo对象指定路径中创建或获取SD卡
	 * */
	public SQLiteOrmHelper(SQLiteOrmSDContext context,IDataBaseInfo info)
	{
		super(context, info.getDataBaseName(), null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1)
	{
		// TODO Auto-generated method stub
		Log.d("demo", "onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3)
	{
		// TODO Auto-generated method stub
		Log.d("demo", "onUpgrade");
	}
	
	public boolean OpenDataBase()
	{
		return getWritableDatabase()==null;
	}

	
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

}
