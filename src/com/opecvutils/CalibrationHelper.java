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
 * �궨�����İ�����
 * */
public class CalibrationHelper
{
	/**���ڴ洢��������ľ���*/
	private Mat cameraMatrix;
	/**���ڴ洢��������ľ���*/
	private Mat distCoeffs;
	/**���ڴ洢�ǵ���������ľ�����*/
	private MatOfPoint2f[] corners;
	/**���ڴ洢����Ӱ��Ľǵ�ֲ��ߴ�*/
	private Size harrisSize;
	/**���ڴ洢����Ӱ���·��*/
	private String[] imagePaths;
	/**���ڱ�ʾ���Ŀ��Ʒ�*/
	private int flags;
	/**
	 * ���캯��<br>
	 * ��ʼ������������󡢻���������������Ʒ��Ȳ���
	 * @param size ����Ӱ��Ľǵ�ֲ��ߴ�
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
	 * �궨����<br>
	 * @param objectPoints ���ڱ궨�﷽����
	 * @param imageSize ���ڱ궨��Ӱ���ͳһ�ߴ�
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
	 * �궨����<br>
	 * �ú�����Ҫ��FeatureLocated���������
	 * @param imageSize ���ڱ궨��Ӱ���ͳһ�ߴ�
	 * @return �궨����ͶӰ�����
	 * **/
	public double Calibrate(Size imageSize)
	{
		return Calibrate(CreateobjectPoints(), imageSize);
	}
	/**
	 * ����Ĭ�ϵı궨���﷽����<br>
	 * �﷽������ʽΪ[mHarris.width*mHarris.height,1]=([0-mHarris.height],[0-mHarris.width],0)
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
	 * ����ָ������ͼ������������ȡ
	 * @param ThumbnailPath ����ȡ�����������ͼ
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
	 * ָ��������Ӱ������������������������ؼ�
	 * @param index ����λ�������ԭʼӰ������<p>
	 * <b>�÷�����Ҫ��setImagePaths���ú󷽿ɵ���</b>
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
	 * ���б���ʽ��ø��Ŵ�����Ӱ��λ�������������	 
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
	 * ������������ڷ�λԪ��
	 * @return 3*3���ڷ�λԪ�ؾ���<p>
	 * <b>�÷�����Ҫ��Calibrate�������ú����</b>
	 * */
	public Mat getCameraMatrix()
	{
		return this.cameraMatrix;
	}

	/**
	 * ���������������Ԫ��
	 * @return 5*1���������Ԫ�ؾ���<p>
	 * <b>�÷�����Ҫ��Calibrate�������ú����</b>
	 * */
	public Mat getDistCoeffs()
	{
		return this.distCoeffs;
	}
	
	/**
	 * ��ô�����Ӱ���·������	 
	 * */
	public String[] getImagePaths()
	{
		return imagePaths;

	}
	/**
	 * ��MatOfPoint2f������ʽ��ø��Ŵ�����Ӱ��λ�������������	 
	 * */
	public MatOfPoint2f[] getSubPixelFeaturePoints()
	{
		return this.corners;
	}
	/**
	 * ���ô�����Ӱ���·������	 
	 * */
	public void setImagePaths(String[] mImagePaths)
	{
		this.imagePaths = mImagePaths;
		this.corners = new MatOfPoint2f[mImagePaths.length];
		for (int i = 0; i < mImagePaths.length; i++)
			this.corners[i] = new MatOfPoint2f();
	}
	
	/**
	 * ��ָ��·���µĴ�����Ӱ����������ǵ�
	 * @param imagepath �����������ǵ�Ĵ�����Ӱ��·��
	 * @return �����������Ƶ�Bitmapͼ��
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
