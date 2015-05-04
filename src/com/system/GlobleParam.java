 package com.system;

import com.tool.SqliteHelperOrm.IDataBaseInfo;



/**ȫ�ֱ���
 * <br><b>���õ���ģʽ��֤����Ψһ</b>
 * */
public class GlobleParam implements IDataBaseInfo
{
	/**ȫ�ֲ�����Ψһʵ��*/
	private static GlobleParam mParam;

	/**
	 * ��ȡ�򴴽�ȫ�ֲ�������
	 * @return ����ȫ�ֲ�����Ψһ����ʵ��
	 * */
	public static GlobleParam Create()
	{
		if (mParam == null)
			mParam = new GlobleParam();
		return mParam;
	}
	/**
	 * ˽�л����캯������ֹ�ⲿ����
	 * */
	private GlobleParam(){}
	/**
	 * ѡ�е�����ͼ·��
	 * */
	private String[] thumbnailImagePath;
	/**
	 * ��ȡȫ�ֱ����е�����ͼ·��
	 * */
	public String[] getThumbnailImagePath()
	{
		return thumbnailImagePath;
	}
	/**
	 * ����ȫ�ֱ����е�����ͼ·��
	 * */
	public void setThumbnailImagePath(String[] thumbnailImagePath)
	{
		this.thumbnailImagePath = thumbnailImagePath;
	}
	/**
	 * ȫ�ֱ����еĴ��ز���ͼ��·��
	 * */
	private String remapImagePath;
	/**
	 * ��ȡȫ�ֱ����еĴ��ز���ͼ��·��
	 * */
	public String getRemapImagePath()
	{
		return remapImagePath;
	}
	/**
	 * ���ñ����еĴ��ز���ͼ��·��
	 * */
	public void setRemapImagePath(String remapImagePath)
	{
		this.remapImagePath = remapImagePath;
	}
	
	@Override
	public String getDataBaseFullPath()
	{
		// TODO Auto-generated method stub
		return SystemUtils.ExtendSDpath()+"/PHM/db/phm.db";
	}

	@Override
	public String getDataBasePath()
	{
		// TODO Auto-generated method stub
		return SystemUtils.ExtendSDpath()+"/PHM/db";
	}

	@Override
	public String getDataBaseName()
	{
		// TODO Auto-generated method stub
		return "phm.db";
	}
	
}
