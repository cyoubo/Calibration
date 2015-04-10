package com.opecvutils;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import FileUtils.FilesOuter.BaseFileOuter;
import FileUtils.FilesOuter.FileoutputListener;
import FileUtils.FilesOuter.WriteFlag;
import android.util.Log;

/**
 * 用于矩阵输出的工具类
 * */
public class MatOutputer 
{
	/**待输出的矩阵*/
	private Mat mat;
	
	/**
	 * 构造函数<br>
	 * @param mat 待输出的矩阵
	 * */
	public MatOutputer(Mat mat)
	{
		this.mat=mat;
	}
	
	/**
	 * 输出到XML文件<br>
	 * 使用OpenCV底层函数Highgui.imwrite将矩阵以xml文件按照指定名字输出到指定路径中
	 * @param path 指定的文件路径(不包含/)
	 * @param name 指定的文件名(不包含后缀)
	 * */
	public boolean PrintToXML(String path,String name)
	{
		return Highgui.imwrite(path+"/"+name+".xml", mat);
	}
	
	/**
	 * 输出到LogCat<br>
	 * 使用Android中的Log.d()函数，将矩阵元素输出到LogCat屏幕
	 * @param key log.d中的tag
	 * */
	public void PrintToLogCat(String key)
	{
		for(int i=0;i<mat.width();i++)
		{
			for(int j=0;j<mat.height();j++)
			{
				double[] value=mat.get(i,j);
				StringBuffer buffer=new StringBuffer();
				buffer.append("(");
				for(int k=0;k<value.length;k++)	
				{
					buffer.append(value[i]);
					buffer.append(",");
				}
				buffer.delete(buffer.length()-1, 1);
				buffer.append(")  ");
				Log.d(key, buffer.toString());
			}
		}
	}
	/**
	 * 输出到Txt文件<br>
	 * 使用FileUtils包中函数将矩阵以csv文件按照指定名字输出到指定路径中
	 * @param path 指定的文件路径(包含/)
	 * @param name 指定的文件名(不包含后缀)
	 * */
	public boolean PrintToTxt(String path,String name)
	{
		boolean result=false;
		MatFileOutputer outputer=new MatFileOutputer();
		BaseFileOuter fileOuter=new BaseFileOuter(path, name+".txt");
		fileOuter.CreateOrOpenFile(WriteFlag.OVERIDE);
		result=fileOuter.Print(outputer);
		fileOuter.Close();
		return result;
	}
	/**
	 * 内部类，用于矩阵的文件输出，实现了FileoutputListener接口
	 * */
	private class MatFileOutputer implements FileoutputListener
	{
		@Override
		public boolean output(OutputStreamWriter out, String path, String name)
		{
			boolean result=false;
			try
			{
				for(int i=0;i<mat.width();i++)
				{
					for(int j=0;j<mat.height();j++)
					{
						double[] value=mat.get(i,j);
						StringBuffer buffer=new StringBuffer();
						buffer.append("(");
						for(int k=0;k<value.length;k++)	
						{
							buffer.append(value[i]);
							buffer.append(",");
						}
						buffer.delete(buffer.length()-1, 1);
						buffer.append(")  ");
						out.write(buffer.toString());
					}
					out.write("\r\n");
					out.flush();
				}
				out.close();
				result=true;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			
			return result;
		}
		
	}
}
