package com.system;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import FileUtils.Utils.DirectoryUtils;
import android.content.Context;

import com.tool.SqliteHelper.ISQLiteDataInterface.IDataBaseInfo;

/** ��������ʵ�ֳ��õ�ȫ�ַ��� */
public class SystemUtils implements IDataBaseInfo
{
	/** ��ȡϵͳ��ʱ�� */
	public static long CurrentSystemTime()
	{
		return System.currentTimeMillis();
	}

	/** ��ȡϵͳ������SD��·�� */
	public static String ExtendSDpath()
	{
		return android.os.Environment.getExternalStorageDirectory().toString();
	}

	/** ��������ת��Ϊָ���������ַ��� */
	public static String ConvertDate(long date)
	{
		return ConvertDate(new Date(date));
	}

	/** ��ʱ�ڶ���ת��Ϊָ���������ַ��� */
	public static String ConvertDate(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		return format.format(date);
	}

	/** ��ȡyyyy-MM-dd_HH:mm:ss��ʽ��ϵͳʱ�� */
	public static String getSystemDateString()
	{
		return SystemUtils.ConvertDate(SystemUtils.CurrentSystemTime());
	}
	
	/** ��ȡyyyy-MM-dd_HH:mm��ʽ��ϵͳʱ�� */
	public static String getSystemDateNosecondString()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
		return format.format(SystemUtils.CurrentSystemTime());
	}

	/** ��ȡ���ڴ洢ͼ��ĵ�ַ�ַ���*/
	public static String getPicturePath()
	{
		return ExtendSDpath()+"/PHM/Photo";
	}

	/** ��ȡ���ڴ洢ͼ������ͼ�ĵ�ַ�ַ��� */
	public static String getPictureThumbnailPath()
	{
		return ExtendSDpath()+"/PHM/Photo/thumbnail";
	}
	/**���ڻ�ȡ��Ļ�ĳߴ�
	 * @return int[] int[0]Ϊ��Ļ��ȣ�1Ϊ�߶� 
	 * */
	public static int[] getWindowSize(Context context)
	{
		int[] result=new int[2];
		android.view.WindowManager manager=(android.view.WindowManager ) context.getSystemService(Context.WINDOW_SERVICE);
		result[1]=manager.getDefaultDisplay().getHeight();
		result[0]=manager.getDefaultDisplay().getWidth();
		return result;
	}
	/**
	 * ���ڽ�����ͼ·��ת��Ϊԭͼ·��
	 * @param ThumbnailPath ��ת��������ͼ·��
	 * @return ת�����ԭͼ·��
	 * */
	public static String ConvetThumbnailPathToImage(String ThumbnailPath)
	{
		String path=DirectoryUtils.SpiltFullPath(ThumbnailPath)[0];
		String name=DirectoryUtils.SpiltFullPath(ThumbnailPath)[1];
		String fatherpath=new File(path).getParent();
		return fatherpath+"/"+name;
	}

	@Override
	public String getDataBaseFullPath()
	{
		// TODO Auto-generated method stub
		return ExtendSDpath()+"/PHM/db/phm.db";
	}

	@Override
	public String getDataBasePath()
	{
		// TODO Auto-generated method stub
		return ExtendSDpath()+"/PHM/db";
	}

	@Override
	public String getDataBaseName()
	{
		// TODO Auto-generated method stub
		return "phm.db";
	}

}
