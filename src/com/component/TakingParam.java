/**
 * 
 */
package com.component;

import java.io.Serializable;
import java.util.List;

import android.hardware.Camera.Size;

/**
 *���ղ�����ģ����
 */
public class TakingParam implements Serializable
{

	/****/
	private static final long serialVersionUID = 2823551582000861079L;

	/** չʾ�������֧�ֵ�ͼ��ֱ��� */
	private SerializableSize[] mysizes;
	/** ��ǰ�����ȡͼ��ֱ��� */
	private SerializableSize currentSize;
	/** �Ƿ�������ı�ʾ�� */
	private boolean IsFlashed;

	public TakingParam()
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
