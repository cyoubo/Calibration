 package com.system;



/**ȫ�ֱ���
 * <br><b>���õ���ģʽ��֤����Ψһ</b>
 * */
public class GlobleParam 
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
