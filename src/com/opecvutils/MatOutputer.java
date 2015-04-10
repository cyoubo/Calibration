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
 * ���ھ�������Ĺ�����
 * */
public class MatOutputer 
{
	/**������ľ���*/
	private Mat mat;
	
	/**
	 * ���캯��<br>
	 * @param mat ������ľ���
	 * */
	public MatOutputer(Mat mat)
	{
		this.mat=mat;
	}
	
	/**
	 * �����XML�ļ�<br>
	 * ʹ��OpenCV�ײ㺯��Highgui.imwrite��������xml�ļ�����ָ�����������ָ��·����
	 * @param path ָ�����ļ�·��(������/)
	 * @param name ָ�����ļ���(��������׺)
	 * */
	public boolean PrintToXML(String path,String name)
	{
		return Highgui.imwrite(path+"/"+name+".xml", mat);
	}
	
	/**
	 * �����LogCat<br>
	 * ʹ��Android�е�Log.d()������������Ԫ�������LogCat��Ļ
	 * @param key log.d�е�tag
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
	 * �����Txt�ļ�<br>
	 * ʹ��FileUtils���к�����������csv�ļ�����ָ�����������ָ��·����
	 * @param path ָ�����ļ�·��(����/)
	 * @param name ָ�����ļ���(��������׺)
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
	 * �ڲ��࣬���ھ�����ļ������ʵ����FileoutputListener�ӿ�
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
