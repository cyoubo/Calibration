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
 * 基于OpenCV函数库的图像帮助类<br>
 * <b>在使用该类前需要保证OpenCV函数库已经连接成功，否则会报错</b>
 * */
public class BitmapHelper_CV 
{
	/**用于处理图像的Mat对象*/
	private Mat mMat;
	/**该图像文件的路径*/
	private String mPath;
	
	/**
	 * 构造函数
	 * @param path 待构建图像矩阵的文件路径
	 * */
	public BitmapHelper_CV(String path)
	{
		mMat=Highgui.imread(path,Highgui.CV_LOAD_IMAGE_UNCHANGED);
		this.mPath=path;
	}
	
	/**
	 * 将图像矩阵转化为BitMap对象
	 * @return 转换后与图像矩阵同尺寸的bitmap对象
	 * */
	public Bitmap ConvertToBitMap()
	{
		Bitmap temp=Bitmap.createBitmap(this.mMat.width(), this.mMat.height(), Config.ARGB_8888);
		Utils.matToBitmap(mMat, temp);
		return temp;
	}
	/**
	 * 获取图像矩阵
	 * */
	public Mat getImageMat()
	{
		return mMat;
	}
	/**
	 * 获取图像路径
	 * */
	public String getmPath()
	{
		return mPath;
	}
	
	/**
	 * 回收图像内存空间
	 * */
	public void ReleaseMat()
	{
		this.mMat.release();
	}
	/**
	 *Mat对象转换为Bitmap对象
	 *@param mat 待转换的mat对象
	 *@return 转换后格式为RGB_565的bitmap对象
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
	 * 通过中心点与边长获得举正方形的ROI
	 * @param center ROI的中心点坐标
	 * @param radius ROI的边长
	 * @return mat类型的ROI<br>
	 * <b>该ROI与原始Mat共享内存空间。修改时需注意</b>
	 * */
	public Mat GetROI_byCenterPoint(PointF center,int radius)
	{
		//CV中利用左上角坐标创建Rect对象，因此中心点需要平移二分之一个radius
		int dis=radius/2;
		Point c=new Point(center.x-dis,center.y-dis);
		Rect r=new Rect(c, new Size(radius, radius));
		return this.mMat.submat(r);
	}
	/**
	 * 通过中心点与边长获得举正方形的ROI
	 * @param center ROI的中心点坐标
	 * @param radius ROI的边长
	 * @return Bitmap类型的ROI<br>
	 * */
	public Bitmap GetROI_byCenterPoint_ToBitmap(PointF center,int radius)
	{
		return BitmapHelper_CV.MatToBitmap(GetROI_byCenterPoint(center, radius));
	}
	/**
	 * 通过中心点集合与边长获得举正方形的ROI集合
	 * @param centerlist ROI的中心点坐标集合
	 * @param radius ROI的边长
	 * @return mat类型的ROI列表集合<br>
	 * <b>列表中的ROI与原始Mat共享内存空间。修改时需注意</b>
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
