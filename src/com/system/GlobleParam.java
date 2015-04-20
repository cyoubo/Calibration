 package com.system;



/**全局变量
 * <br><b>采用单件模式保证数据唯一</b>
 * */
public class GlobleParam 
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
	private GlobleParam()
	{
		
	}
	
	private String[] thumbnailImagePath;
	
	public String[] getThumbnailImagePath()
	{
		return thumbnailImagePath;
	}
	
	public void setThumbnailImagePath(String[] thumbnailImagePath)
	{
		this.thumbnailImagePath = thumbnailImagePath;
	}
	
	private String remapImagePath;

	public String getRemapImagePath()
	{
		return remapImagePath;
	}
	public void setRemapImagePath(String remapImagePath)
	{
		this.remapImagePath = remapImagePath;
	}
}
