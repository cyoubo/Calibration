 package com.system;

import com.tool.SqliteHelperOrm.IDataBaseInfo;



/**全局变量
 * <br><b>采用单件模式保证数据唯一</b>
 * */
public class GlobleParam implements IDataBaseInfo
{
	/**全局参数的唯一实例*/
	private static GlobleParam mParam;

	/**
	 * 获取或创建全局参数变量
	 * @return 存有全局参数的唯一变量实例
	 * */
	public static GlobleParam Create()
	{
		if (mParam == null)
			mParam = new GlobleParam();
		return mParam;
	}
	/**
	 * 私有化构造函数，防止外部构造
	 * */
	private GlobleParam(){}
	/**
	 * 选中的缩略图路径
	 * */
	private String[] thumbnailImagePath;
	/**
	 * 获取全局变量中的缩略图路径
	 * */
	public String[] getThumbnailImagePath()
	{
		return thumbnailImagePath;
	}
	/**
	 * 设置全局变量中的缩略图路径
	 * */
	public void setThumbnailImagePath(String[] thumbnailImagePath)
	{
		this.thumbnailImagePath = thumbnailImagePath;
	}
	/**
	 * 全局变量中的待重采样图像路径
	 * */
	private String remapImagePath;
	/**
	 * 获取全局变量中的待重采样图像路径
	 * */
	public String getRemapImagePath()
	{
		return remapImagePath;
	}
	/**
	 * 设置变量中的待重采样图像路径
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
