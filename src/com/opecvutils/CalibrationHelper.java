package com.opecvutils;

import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;

/**
 * 标定操作的帮助类
 * */
public class CalibrationHelper
{
	/**用于存储相机参数的矩阵*/
	private Mat cameraMatrix;
	/**用于存储畸变参数的矩阵*/
	private Mat distCoeffs;
	/**用于存储角点坐标参数的矩阵组*/
	private MatOfPoint2f[] corners;
	/**用于存储解算影像的角点分布尺寸*/
	private Size harrisSize;
	/**用于存储解算影像的路径*/
	private String[] imagePaths;
	/**用于表示求解的控制符*/
	private int flags;
	/**
	 * 构造函数<br>
	 * 初始化相机参数矩阵、畸变参数矩阵、求解控制符等参数
	 * @param size 解算影像的角点分布尺寸
	 * */
	public CalibrationHelper(Size size)
	{
		this.harrisSize = size;
		flags = Calib3d.CALIB_FIX_PRINCIPAL_POINT
				+ Calib3d.CALIB_ZERO_TANGENT_DIST
				+ Calib3d.CALIB_FIX_ASPECT_RATIO + Calib3d.CALIB_FIX_K3
				+ Calib3d.CALIB_FIX_K4;
		cameraMatrix = Mat.eye(3, 3, CvType.CV_32FC1);
		cameraMatrix.put(0, 0, 1.0);
		distCoeffs = Mat.zeros(5, 1, CvType.CV_64FC1);
	}

	/**
	 * 标定处理<br>
	 * @param objectPoints 用于标定物方坐标
	 * @param imageSize 用于标定的影像的统一尺寸
	 * **/
	public double Calibrate(List<Mat> objectPoints, Size imageSize)
	{
		List<Mat> tvecs = new ArrayList<>();
		List<Mat> rvecs = new ArrayList<>();
		for (int index = 0; index < this.corners.length; index++)
		{
			tvecs.add(new Mat());
			rvecs.add(new Mat());
		}
		double result = Calib3d.calibrateCamera(objectPoints,
				this.getSubPixelFeaturePointsList(), imageSize, cameraMatrix,
				distCoeffs, rvecs, tvecs, flags);
		return result;
	}
	/**
	 * 标定处理<br>
	 * 该函数需要在FeatureLocated函数后调用
	 * @param imageSize 用于标定的影像的统一尺寸
	 * @return 标定的重投影中误差
	 * **/
	public double Calibrate(Size imageSize)
	{
		return Calibrate(CreateobjectPoints(), imageSize);
	}
	/**
	 * 创建默认的标定用物方坐标<br>
	 * 物方坐标形式为[mHarris.width*mHarris.height,1]=([0-mHarris.height],[0-mHarris.width],0)
	 **/
	public List<Mat> CreateobjectPoints()
	{
		List<Mat> result = new ArrayList<>();
		for (int index = 0; index < this.imagePaths.length; index++)
		{
			Mat temp =new Mat(new Size(1, harrisSize.height*harrisSize.width), CvType.CV_32FC3);
			for (int i = 0; i < temp.rows(); i++)
				for (int j = 0; j < temp.cols(); j++)
					temp.put(i, j, new double[]{i%harrisSize.width,i/harrisSize.width,0});
			
			result.add(temp);
		}
		return result;
	}
	/**
	 * 根据指定缩略图完成特征点的提取
	 * @param ThumbnailPath 待提取特征点的缩略图
	 * */
	public boolean FeatureDetectbyThumbnail(String ThumbnailPath)
	{
		MatOfPoint2f tempconcers = new MatOfPoint2f();
		BitmapHelper_CV helper_CV = new BitmapHelper_CV(ThumbnailPath);
		boolean result = Calib3d.findChessboardCorners(helper_CV.getImageMat(),
				harrisSize, tempconcers, Calib3d.CALIB_CB_ADAPTIVE_THRESH);
		helper_CV.ReleaseMat();
		return result;
	}
	/**
	 * 指定待解算影像的索引号完成特征点的亚像素级
	 * @param index 待定位特征点的原始影像索引<p>
	 * <b>该方法需要在setImagePaths调用后方可调用</b>
	 * */
	public boolean  FeatureLocated(int index)
	{
		BitmapHelper_CV helper_CV = new BitmapHelper_CV(imagePaths[index]);
		boolean result=Calib3d.findChessboardCorners(helper_CV.getImageMat(), harrisSize,
				corners[index], Calib3d.CALIB_CB_ADAPTIVE_THRESH);
		TermCriteria criteria = new TermCriteria(TermCriteria.EPS
				| TermCriteria.COUNT, 100, 0.001);
		Imgproc.cornerSubPix(helper_CV.getImageMatAsSingelChannel(),
				corners[index], new Size(5, 5), new Size(-1, -1), criteria);
		helper_CV.ReleaseMat();
		return result;
	}

	/**
	 * 以列表形式获得各张待解算影像定位后的特征点坐标	 
	 * */
	public List<Mat> getSubPixelFeaturePointsList()
	{
		List<Mat> result = new ArrayList<>();
		for (int index = 0; index < this.corners.length; index++)
		{
			result.add(corners[index]);
		}
		return result;
	}
	
	/**
	 * 获得求解后的相机内方位元素
	 * @return 3*3的内方位元素矩阵<p>
	 * <b>该方法需要在Calibrate方法调用后调用</b>
	 * */
	public Mat getCameraMatrix()
	{
		return this.cameraMatrix;
	}

	/**
	 * 获得求解后的相机畸变元素
	 * @return 5*1的相机畸变元素矩阵<p>
	 * <b>该方法需要在Calibrate方法调用后调用</b>
	 * */
	public Mat getDistCoeffs()
	{
		return this.distCoeffs;
	}
	
	/**
	 * 获得待解算影像的路径数组	 
	 * */
	public String[] getImagePaths()
	{
		return imagePaths;

	}
	/**
	 * 以MatOfPoint2f数组形式获得各张待解算影像定位后的特征点坐标	 
	 * */
	public MatOfPoint2f[] getSubPixelFeaturePoints()
	{
		return this.corners;
	}
	/**
	 * 设置待解算影像的路径数组	 
	 * */
	public void setImagePaths(String[] mImagePaths)
	{
		this.imagePaths = mImagePaths;
		this.corners = new MatOfPoint2f[mImagePaths.length];
		for (int i = 0; i < mImagePaths.length; i++)
			this.corners[i] = new MatOfPoint2f();
	}
	
	/**
	 * 对指定路径下的待解算影像绘制特征角点
	 * @param imagepath 待绘制特征角点的待解算影像路径
	 * @return 完成特征点绘制的Bitmap图像
	 * */
	public Bitmap DrawHarris(String imagepath)
	{
		MatOfPoint2f tempconcers = new MatOfPoint2f();
		BitmapHelper_CV helper_CV = new BitmapHelper_CV(imagepath);
		boolean result = Calib3d.findChessboardCorners(helper_CV.getImageMat(),
				harrisSize, tempconcers, Calib3d.CALIB_CB_ADAPTIVE_THRESH);
		
		Calib3d.drawChessboardCorners(helper_CV.getImageMat(), harrisSize, tempconcers, result);
		
		return helper_CV.ConvertToBitMap();
	}
}
