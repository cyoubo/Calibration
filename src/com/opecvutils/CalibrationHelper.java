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
import android.util.Log;

/**
 * �궨�����İ�����
 * */
public class CalibrationHelper
{
	/**���ڴ洢��������ľ���*/
	private Mat mCameraMatrix;
	/**���ڴ洢��������ľ���*/
	private Mat mDistCoeffs;
	/**���ڴ洢�ǵ���������ľ�����*/
	private MatOfPoint2f[] mCorners;
	/**���ڴ洢����Ӱ��Ľǵ�ֲ��ߴ�*/
	private Size mHarrisSize;
	/**���ڴ洢����Ӱ���·��*/
	private String[] mImagePaths;
	/**���ڱ�ʾ���Ŀ��Ʒ�*/
	private int mFlags;
	/**
	 * ���캯��<br>
	 * ��ʼ������������󡢻���������������Ʒ��Ȳ���
	 * @param size ����Ӱ��Ľǵ�ֲ��ߴ�
	 * */
	public CalibrationHelper(Size size)
	{
		this.mHarrisSize = size;
		mFlags = Calib3d.CALIB_FIX_PRINCIPAL_POINT
				+ Calib3d.CALIB_ZERO_TANGENT_DIST
				+ Calib3d.CALIB_FIX_ASPECT_RATIO + Calib3d.CALIB_FIX_K4
				+ Calib3d.CALIB_FIX_K5;
		mCameraMatrix = Mat.eye(3, 3, CvType.CV_32FC1);
		mCameraMatrix.put(0, 0, 1.0);
		mDistCoeffs = Mat.zeros(5, 1, CvType.CV_64FC1);
	}

	/**
	 * �궨����<br>
	 * @param objectPoints ���ڱ궨�﷽����
	 * @param imageSize ���ڱ궨��Ӱ���ͳһ�ߴ�
	 * 
	 * **/
	public double Calibration(List<Mat> objectPoints, Size imageSize)
	{
		List<Mat> tvecs = new ArrayList<>();
		List<Mat> rvecs = new ArrayList<>();
		for (int index = 0; index < this.mCorners.length; index++)
		{
			tvecs.add(new Mat());
			rvecs.add(new Mat());
		}
		double result = Calib3d.calibrateCamera(objectPoints,
				this.getCornerAsListMat(), imageSize, mCameraMatrix,
				mDistCoeffs, rvecs, tvecs, mFlags);
		return result;
	}
	/**
	 * �궨����<br>
	 * �ú�����Ҫ��FeatureLocated���������
	 * @param imageSize ���ڱ궨��Ӱ���ͳһ�ߴ�
	 * @return �궨����ͶӰ�����
	 * **/
	public double Calibration(Size imageSize)
	{
		return Calibration(CreateobjectPoints(), imageSize);
	}
	/**
	 * ����Ĭ�ϵı궨���﷽����<br>
	 * **/
	public List<Mat> CreateobjectPoints()
	{
		List<Mat> result = new ArrayList<>();
		for (int index = 0; index < this.mImagePaths.length; index++)
		{
			Mat temp =new Mat(mHarrisSize, CvType.CV_32FC1);
			for (int i = 0; i < mHarrisSize.width; i++)
				for (int j = 0; j < mHarrisSize.height; j++)
					temp.put(i, j, new double[]{i,j,0});
			
			result.add(temp);
		}
		return result;
	}

	public Integer[] FeatureDetect()
	{
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < mImagePaths.length; i++)
		{
			BitmapHelper_CV helper_CV = new BitmapHelper_CV(mImagePaths[i]);
			boolean r = Calib3d.findChessboardCorners(helper_CV.getImageMat(),
					mHarrisSize, mCorners[i], Calib3d.CALIB_CB_ADAPTIVE_THRESH);
			if (r)
				result.add(i);
			helper_CV.ReleaseMat();
		}
		return (Integer[]) result.toArray();
	}	
	public boolean FeatureDetectbyThumbnail(String ThumbnailPath)
	{
		MatOfPoint2f tempconcers = new MatOfPoint2f();
		BitmapHelper_CV helper_CV = new BitmapHelper_CV(ThumbnailPath);
		boolean result = Calib3d.findChessboardCorners(helper_CV.getImageMat(),
				mHarrisSize, tempconcers, Calib3d.CALIB_CB_ADAPTIVE_THRESH);
		helper_CV.ReleaseMat();
		
		Log.i("demo",ThumbnailPath+" "+result);
		
		return result;
	}

	public void FeatureLocated()
	{
		for (int i = 0; i < mImagePaths.length; i++)
		{
			FeatureLocated(i);
		}
	}

	public boolean  FeatureLocated(int index)
	{
		BitmapHelper_CV helper_CV = new BitmapHelper_CV(mImagePaths[index]);
		boolean result=Calib3d.findChessboardCorners(helper_CV.getImageMat(), mHarrisSize,
				mCorners[index], Calib3d.CALIB_CB_ADAPTIVE_THRESH);
		TermCriteria criteria = new TermCriteria(TermCriteria.EPS
				| TermCriteria.COUNT, 100, 0.001);
		Imgproc.cornerSubPix(helper_CV.getImageMatAsSingelChannel(),
				mCorners[index], new Size(5, 5), new Size(-1, -1), criteria);
		helper_CV.ReleaseMat();
		return result;
	}

	public Mat getCameraMatrix()
	{
		return this.mCameraMatrix;
	}

	public List<Mat> getCornerAsListMat()
	{
		List<Mat> result = new ArrayList<>();
		for (int index = 0; index < this.mCorners.length; index++)
		{
			result.add(mCorners[index]);
		}
		return result;
	}

	public Mat getDistCoeffs()
	{
		return this.mDistCoeffs;
	}

	public String[] getImagePaths()
	{
		return mImagePaths;

	}

	public MatOfPoint2f[] getResultMatofPoint()
	{
		return this.mCorners;
	}

	public void setImagePaths(String[] mImagePaths)
	{
		this.mImagePaths = mImagePaths;
		this.mCorners = new MatOfPoint2f[mImagePaths.length];
		for (int i = 0; i < mImagePaths.length; i++)
			this.mCorners[i] = new MatOfPoint2f();
	}
	
	public Bitmap DrawHarris(String imagepath)
	{
		MatOfPoint2f tempconcers = new MatOfPoint2f();
		BitmapHelper_CV helper_CV = new BitmapHelper_CV(imagepath);
		boolean result = Calib3d.findChessboardCorners(helper_CV.getImageMat(),
				mHarrisSize, tempconcers, Calib3d.CALIB_CB_ADAPTIVE_THRESH);
		
		
		Calib3d.drawChessboardCorners(helper_CV.getImageMat(), mHarrisSize, tempconcers, result);
		
		return helper_CV.ConvertToBitMap();
	}
}
