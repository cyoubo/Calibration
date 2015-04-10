package com.opecvutils;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PointF;


/**
 * ����OpenCV�������ͼ�������<br>
 * <b>��ʹ�ø���ǰ��Ҫ��֤OpenCV�������Ѿ����ӳɹ�������ᱨ��</b>
 * */
public class BitmapHelper_CV 
{
	/**���ڴ���ͼ���Mat����*/
	private Mat mMat;
	/**��ͼ���ļ���·��*/
	private String mPath;
	
	/**
	 * ���캯��
	 * @param path ������ͼ�������ļ�·��
	 * */
	public BitmapHelper_CV(String path)
	{
		mMat=Highgui.imread(path,Highgui.CV_LOAD_IMAGE_UNCHANGED);
		this.mPath=path;
	}
	
	/**
	 * ��ͼ�����ת��ΪBitMap����
	 * @return ת������ͼ�����ͬ�ߴ��bitmap����
	 * */
	public Bitmap ConvertToBitMap()
	{
		Bitmap temp=Bitmap.createBitmap(this.mMat.width(), this.mMat.height(), Config.ARGB_8888);
		Utils.matToBitmap(mMat, temp);
		return temp;
	}
	/**
	 * ��ȡͼ�����
	 * */
	public Mat getImageMat()
	{
		return mMat;
	}
	/**
	 * ��ȡͼ��·��
	 * */
	public String getmPath()
	{
		return mPath;
	}
	
	/**
	 * ����ͼ���ڴ�ռ�
	 * */
	public void ReleaseMat()
	{
		this.mMat.release();
	}
	/**
	 *Mat����ת��ΪBitmap����
	 *@param mat ��ת����mat����
	 *@return ת�����ʽΪRGB_565��bitmap����
	 * */
	public static Bitmap MatToBitmap(Mat mat)
	{
		Bitmap temp=Bitmap.createBitmap(mat.width(), mat.height(), Config.ARGB_8888);
		Utils.matToBitmap(mat, temp);
		return temp;
	}
	
	public int getWidth()
	{
		return mMat.width();
	}
	
	public int getHeight()
	{
		return mMat.height();
	}
	/**
	 * ͨ�����ĵ���߳���þ������ε�ROI
	 * @param center ROI�����ĵ�����
	 * @param radius ROI�ı߳�
	 * @return mat���͵�ROI<br>
	 * <b>��ROI��ԭʼMat�����ڴ�ռ䡣�޸�ʱ��ע��</b>
	 * */
	public Mat GetROI_byCenterPoint(PointF center,int radius)
	{
		//CV���������Ͻ����괴��Rect����������ĵ���Ҫƽ�ƶ���֮һ��radius
		int dis=radius/2;
		Point c=new Point(center.x-dis,center.y-dis);
		Rect r=new Rect(c, new Size(radius, radius));
		return this.mMat.submat(r);
	}
	/**
	 * ͨ�����ĵ���߳���þ������ε�ROI
	 * @param center ROI�����ĵ�����
	 * @param radius ROI�ı߳�
	 * @return Bitmap���͵�ROI<br>
	 * */
	public Bitmap GetROI_byCenterPoint_ToBitmap(PointF center,int radius)
	{
		return BitmapHelper_CV.MatToBitmap(GetROI_byCenterPoint(center, radius));
	}
	/**
	 * ͨ�����ĵ㼯����߳���þ������ε�ROI����
	 * @param centerlist ROI�����ĵ����꼯��
	 * @param radius ROI�ı߳�
	 * @return mat���͵�ROI�б���<br>
	 * <b>�б��е�ROI��ԭʼMat�����ڴ�ռ䡣�޸�ʱ��ע��</b>
	 * */
	public List<Mat> GetROI_byCenterlist(List<PointF> centerlist,int radius)
	{
		List<Mat> result=new ArrayList<>();
		for (PointF point : centerlist)
			result.add(GetROI_byCenterPoint(point, radius));
		return result;
	}
	
	public Mat getImageMatAsSingelChannel()
	{
		Mat dst=new Mat(mMat.size(), CvType.CV_8UC1);
		Imgproc.cvtColor(mMat, dst, Imgproc.COLOR_BGR2GRAY);
		return dst;
	}
	
	public Mat getUndistoredMat(Mat CamerMatrix,Mat DistCoeffs)
	{
		Mat result=new Mat(this.mMat.size(), this.mMat.type());
		Imgproc.undistort(this.mMat, result, CamerMatrix, DistCoeffs);
		return result;
	}
	
	public Bitmap getUndistoredBitmap(Mat CamerMatrix,Mat DistCoeffs)
	{
		return BitmapHelper_CV.MatToBitmap(getUndistoredMat(CamerMatrix, DistCoeffs));
	}
}
