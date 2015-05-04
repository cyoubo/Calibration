package com.component;

import java.io.Serializable;
import java.util.List;

import android.hardware.Camera.Size;

/**
 * �Զ���Ŀ����л���Size����
 * <p>
 * ��Ҫ���ڽ��Camera.size�����ⲿ�����벻�����л�������
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
	 * ת������:
	 * <p>
	 * ��Camer.size�б�ת��ΪSerializableSize����
	 * 
	 * @param list
	 *            ��ת����Camer.size�б�
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
