package com.calibration.beanshelper;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import com.calibration.beans.CalibrationResultBeans;
import com.component.SQLiteOrmHelperPHM;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;

public class CalibrationResultBeansHelper
{
	CalibrationResultBeans beans;
	public CalibrationResultBeansHelper(CalibrationResultBeans beans)
	{
		this.beans=beans;
	}
	
	public CalibrationResultBeans getBeans()
	{
		return beans;
	}

	public void setRms(double rms)
	{
		this.beans.setRms(rms);
	}

	public void setDistMatrix(Mat mat)
	{
		this.beans.setK1(mat.get(0,0)[0]);
		this.beans.setK2(mat.get(1,0)[0]);
		this.beans.setP1(mat.get(3,0)[0]);
		this.beans.setP2(mat.get(4,0)[0]);
	}

	public void setCammerMatrix(Mat mat)
	{
		this.beans.setFx(mat.get(0, 0)[0]);
		this.beans.setFy(mat.get(1, 1)[0]);
		this.beans.setCx(mat.get(0, 2)[0]);
		this.beans.setCy(mat.get(1, 2)[0]);
	}


	public void setResolution(int width,int height)
	{
		this.beans.setResolution(""+ width +"*" +height);
	}

	public void setResultdate(String date)
	{
		this.beans.setResultdate(date);
	}

	public  String getCalibrationNo()
	{
		return beans.getResolution() +"||"+ beans.getResultdate();
	}

	/**
	 * 根据单一条件表达式对数据库记录进行查询
	 * */
	public static List<CalibrationResultBeans> queryForEq(SQLiteOrmSDContext context,String field,Object value)
	{
		List<CalibrationResultBeans> resultBeans=new ArrayList<>();
		SQLiteOrmHelperPHM helper=new SQLiteOrmHelperPHM(context);
		RuntimeExceptionDao<CalibrationResultBeans,Integer> eDao=helper.getCalibrationRuntimeExceptionDao();
		resultBeans=eDao.queryForEq(field, value);
		helper.close();
		return resultBeans;
	}


}
