/**
 * 
 */
package com.calibration.dao;

import java.io.Serializable;
import java.util.List;



import com.component.SerializableSize;

import android.hardware.Camera.Size;

/**
 * @author Administrator
 *         <p>
 *         拍照参数的模型类
 */
public class TakingParamDao implements Serializable
{

	/****/
	private static final long serialVersionUID = 2823551582000861079L;

	/** 展示的相机所支持的图像分辨率 */
	private SerializableSize[] mysizes;
	/** 当前相机获取图像分辨率 */
	private SerializableSize currentSize;
	/** 是否开启闪光的标示符 */
	private boolean IsFlashed;

	public TakingParamDao()
	{
		this.mysizes = new SerializableSize[5];
		IsFlashed = false;
	}

	public SerializableSize getCurrentSize()
	{
		return currentSize;
	}

	public SerializableSize[] getMysizes()
	{
		return mysizes;
	}

	public boolean isIsFlashed()
	{
		return IsFlashed;
	}

	public void setCurrentSize(SerializableSize currentSize)
	{
		this.currentSize = currentSize;
	}

	public void setIsFlashed(boolean isFlashed)
	{
		IsFlashed = isFlashed;
	}

	public void setmList(List<Size> mList)
	{
		this.mysizes = SerializableSize.ConvertFromList(mList);
		this.currentSize = mysizes[0];
	}

	public String CurrentSize_String()
	{
		return String
				.format("%5d x %5d", currentSize.height, currentSize.width);
	}
}
