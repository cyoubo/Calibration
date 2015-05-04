package com.component;

import java.io.Serializable;
import java.util.List;

import android.hardware.Camera.Size;

/**
 * 自定义的可序列化的Size对象
 * <p>
 * 主要用于解决Camera.size不可外部创建与不可序列化的问题
 */
public class SerializableSize implements Serializable
{
	private static final long serialVersionUID = -3588938789480302929L;

	public int width;
	public int height;

	public SerializableSize(int wid, int hei)
	{
		this.width = wid;
		this.height = hei;
	}

	public SerializableSize(Size size)
	{
		this.width = size.width;
		this.height = size.height;
	}

	/**
	 * 转化方法:
	 * <p>
	 * 将Camer.size列表转化为SerializableSize数组
	 * 
	 * @param list
	 *            待转化的Camer.size列表
	 * */
	public static SerializableSize[] ConvertFromList(List<Size> list)
	{
		SerializableSize temp[] = new SerializableSize[list.size()];
		for (int i = 0; i < temp.length; i++)
		{
			temp[i] = new SerializableSize(list.get(i).width,
					list.get(i).height);
		}
		return temp;
	}

}
