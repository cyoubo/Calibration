package com.calibration.beanshelper;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.calibration.beans.CalibrationResultBeans;
import com.component.SQLiteOrmHelperPHM;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tool.SqliteHelperOrm.SQLiteOrmSDContext;

/**
 * 标定结果Dao,由于与Ormlite中dao泛型重名，改名为后缀为helper
 * */
public class CalibrationResultHelper
{
	/**当前helper需要操作的beans对象*/
	CalibrationResultBeans beans;
	/**
	 * 构造函数<br>
	 * 由需要操作的bean构建helper
	 * @param beans 待操作的beans
	 * */
	public CalibrationResultHelper(CalibrationResultBeans beans)
	{
		this.beans=beans;
	}
	/**
	 * 获取当前helper操作的bean对象
	 * */
	public CalibrationResultBeans getBeans()
	{
		return beans;
	}
	/**
	 * 获取当前beans中的rms
	 * */
	public void setRms(double rms)
	{
		this.beans.setRms(rms);
	}
	/**
	 * 获取当前beans中的DistMatrix
	 * */
	public void setDistMatrix(Mat mat)
	{
		this.beans.setK1(mat.get(0,0)[0]);
		this.beans.setK2(mat.get(1,0)[0]);
		this.beans.setP1(mat.get(2,0)[0]);
		this.beans.setP2(mat.get(3,0)[0]);
	}
	/**
	 * 获得当前beans中的DistMatrix
	 * */
	public Mat getDistMatrix()
	{
		Mat mat=new Mat(4, 1, CvType.CV_32F);
		mat.put(0, 0, this.beans.getK1());
		mat.put(1, 0, this.beans.getK2());
		mat.put(2, 0, this.beans.getP1());
		mat.put(3, 0, this.beans.getP2());
		
		return mat;
	}
	/**
	 * 设置当前beans中的CammerMatrix
	 * */
	public void setCammerMatrix(Mat mat)
	{
		this.beans.setFx(mat.get(0, 0)[0]);
		this.beans.setFy(mat.get(1, 1)[0]);
		this.beans.setCx(mat.get(0, 2)[0]);
		this.beans.setCy(mat.get(1, 2)[0]);
	}
	/**
	 * 获得当前beans中的CammerMatrix
	 * */
	public Mat getCameraMatrix()
	{
		Mat mat=Mat.eye(3, 3, CvType.CV_32F);
		mat.put(0, 0, this.beans.getFx());
		mat.put(1, 1, this.beans.getFy());
		mat.put(0, 2, this.beans.getCx());
		mat.put(1, 2, this.beans.getCy());
		return mat;
	}
	/**
	 * 设置当前beans中的Resolution
	 * */
	public void setResolution(int width,int height)
	{
		this.beans.setResolution(""+ width +"*" +height);
	}
	/**
	 * 设置当前beans中的Resultdate
	 * */
	public void setResultdate(String date)
	{
		this.beans.setResultdate(date);
	}
	/**
	 * 获得当前beans中的“分辨率||处理时间”的简称
	 * */
	public  String getCalibrationShortName()
	{
		return beans.getResolution() +"||"+ beans.getResultdate();
	}
	/**
	 * 获得当前beans中的“序号--分辨率--rms”的长称
	 * */
	public String getCalibrationLongName()
	{
		return String.format("%2d--%s--%.4f", beans.getIndex(),beans.getResolution(),beans.getRms());
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
	
	/**
	 * 对实体表进行全遍历
	 * */
	public static List<CalibrationResultBeans> queryAll(SQLiteOrmSDContext context)
	{
		List<CalibrationResultBeans> resultBeans=new ArrayList<>();
		SQLiteOrmHelperPHM helper=new SQLiteOrmHelperPHM(context);
		RuntimeExceptionDao<CalibrationResultBeans,Integer> eDao=helper.getCalibrationRuntimeExceptionDao();
		resultBeans=eDao.queryForAll();
//		for (CalibrationResultBeans calibrationResultBeans : resultBeans)
//		{
//			Log.d("demo", calibrationResultBeans.toString());
//		}
		helper.close();
		return resultBeans;
		
	}


}
