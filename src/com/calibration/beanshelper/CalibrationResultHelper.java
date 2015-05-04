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
 * �궨���Dao,������Ormlite��dao��������������Ϊ��׺Ϊhelper
 * */
public class CalibrationResultHelper
{
	/**��ǰhelper��Ҫ������beans����*/
	CalibrationResultBeans beans;
	/**
	 * ���캯��<br>
	 * ����Ҫ������bean����helper
	 * @param beans ��������beans
	 * */
	public CalibrationResultHelper(CalibrationResultBeans beans)
	{
		this.beans=beans;
	}
	/**
	 * ��ȡ��ǰhelper������bean����
	 * */
	public CalibrationResultBeans getBeans()
	{
		return beans;
	}
	/**
	 * ��ȡ��ǰbeans�е�rms
	 * */
	public void setRms(double rms)
	{
		this.beans.setRms(rms);
	}
	/**
	 * ��ȡ��ǰbeans�е�DistMatrix
	 * */
	public void setDistMatrix(Mat mat)
	{
		this.beans.setK1(mat.get(0,0)[0]);
		this.beans.setK2(mat.get(1,0)[0]);
		this.beans.setP1(mat.get(2,0)[0]);
		this.beans.setP2(mat.get(3,0)[0]);
	}
	/**
	 * ��õ�ǰbeans�е�DistMatrix
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
	 * ���õ�ǰbeans�е�CammerMatrix
	 * */
	public void setCammerMatrix(Mat mat)
	{
		this.beans.setFx(mat.get(0, 0)[0]);
		this.beans.setFy(mat.get(1, 1)[0]);
		this.beans.setCx(mat.get(0, 2)[0]);
		this.beans.setCy(mat.get(1, 2)[0]);
	}
	/**
	 * ��õ�ǰbeans�е�CammerMatrix
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
	 * ���õ�ǰbeans�е�Resolution
	 * */
	public void setResolution(int width,int height)
	{
		this.beans.setResolution(""+ width +"*" +height);
	}
	/**
	 * ���õ�ǰbeans�е�Resultdate
	 * */
	public void setResultdate(String date)
	{
		this.beans.setResultdate(date);
	}
	/**
	 * ��õ�ǰbeans�еġ��ֱ���||����ʱ�䡱�ļ��
	 * */
	public  String getCalibrationShortName()
	{
		return beans.getResolution() +"||"+ beans.getResultdate();
	}
	/**
	 * ��õ�ǰbeans�еġ����--�ֱ���--rms���ĳ���
	 * */
	public String getCalibrationLongName()
	{
		return String.format("%2d--%s--%.4f", beans.getIndex(),beans.getResolution(),beans.getRms());
	}

	/**
	 * ���ݵ�һ��������ʽ�����ݿ��¼���в�ѯ
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
	 * ��ʵ�������ȫ����
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