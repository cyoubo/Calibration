package com.tool;

import java.io.IOException;

import android.media.ExifInterface;

public class ExifInfoHelper
{
	 private ExifInterface exif;
	 
	 public ExifInfoHelper(String path)
	 {
		 try
		 		{exif=new ExifInterface(path);}
		catch (IOException e)
				{e.printStackTrace();}
	 }
	 
	 public boolean IsCreateHelper()
	 {
		 return exif!=null;
	 }
	 
	 public void setAttitude(String tag,String value)
	 {
		 exif.setAttribute(tag, value);
	 }
	 
	 public String getAttitude(String tag)
	 {
		 return exif.getAttribute(tag);
	 }
	  
	 
}
